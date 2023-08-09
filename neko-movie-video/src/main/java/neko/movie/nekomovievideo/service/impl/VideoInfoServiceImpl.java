package neko.movie.nekomovievideo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.VideoStatus;
import neko.movie.nekomoviecommonbase.utils.exception.ElasticSearchUpdateException;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.entity.CategoryInfo;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.service.CategoryInfoService;
import neko.movie.nekomovievideo.service.VideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.vo.UpdateVideoInfoVo;
import neko.movie.nekomovievideo.vo.VideoInfoVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 影视信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
@Slf4j
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {
    @Resource
    private OSSFeignService ossFeignService;

    @Resource
    private CategoryInfoService categoryInfoService;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 添加影视信息
     */
    @Override
    public void newVideoInfo(String videoName,
                             String videoDescription,
                             String videoProducer,
                             String videoActors,
                             Integer categoryId,
                             LocalDateTime upTime,
                             MultipartFile file) throws InterruptedException {
        //获取分布式锁
        RLock lock = redissonClient.getLock(Constant.VIDEO_REDIS_PREFIX + "lock:level_category:" + categoryId);
        //尝试加锁，最多等待100秒，上锁以后1分钟自动解锁
        boolean isLock = lock.tryLock(100, 1, TimeUnit.SECONDS);

        if(isLock){
            try {
                //分类id不存在
                if(categoryInfoService.getById(categoryId) == null){
                    return;
                }

                //上传图片到oss
                ResultObject<String> r = ossFeignService.uploadImage(file);
                if(!r.getResponseCode().equals(200)){
                    throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
                }

                String videoImage = r.getResult();
                VideoInfo videoInfo = new VideoInfo();
                LocalDateTime now = LocalDateTime.now();
                videoInfo.setVideoName(videoName)
                        .setVideoDescription(videoDescription)
                        .setVideoImage(videoImage)
                        .setVideoProducer(videoProducer)
                        .setVideoActors(videoActors)
                        .setCategoryId(categoryId)
                        .setUpTime(upTime)
                        .setCreateTime(now)
                        .setUpdateTime(now);

                this.baseMapper.insert(videoInfo);
            }finally {
                lock.unlock();
            }
        }else{
            throw new InterruptedException("获取分布式锁失败");
        }
    }

    /**
     * 上架影视视频
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upVideo(String videoInfoId) {
        VideoInfo videoInfo = this.baseMapper.selectById(videoInfoId);
        if(videoInfo == null){
            throw new NoSuchResultException("无此videoInfoId影视信息");
        }

        //获取分类信息
        CategoryInfo categoryInfo = categoryInfoService.getById(videoInfo.getCategoryId());
        VideoInfo todoVideInfo = new VideoInfo();
        todoVideInfo.setVideoInfoId(videoInfoId)
                .setStatus(VideoStatus.UP);

        this.baseMapper.updateById(todoVideInfo);

        //将影视视频信息收集为elasticsearch形式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        VideoInfoES videoInfoES = new VideoInfoES();
        BeanUtil.copyProperties(videoInfo, videoInfoES);
        videoInfoES.setCategoryName(categoryInfo.getCategoryName())
                .setUpTime(videoInfo.getUpTime().format(dateTimeFormatter));

        //向elasticsearch添加商品信息
        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.operations(operation->operation.index(idx->idx.index(Constant.ELASTIC_SEARCH_INDEX)
                .id(videoInfoId)
                .document(videoInfoES)));
        BulkResponse bulkResponse;
        try {
            bulkResponse = elasticsearchClient.bulk(builder.build());
        }catch (Exception e){
            throw new ElasticSearchUpdateException("elasticsearch添加错误");
        }
        if(bulkResponse.errors()){
            throw new ElasticSearchUpdateException("elasticsearch添加错误");
        }

        log.info("影视视频上架videoInfoId: " + videoInfoId + "，" + bulkResponse);
    }

    /**
     * 分页查询影视视频信息
     */
    @Override
    public Page<VideoInfo> getVideoInfoByQueryLimitedPage(QueryVo vo) {
        Page<VideoInfo> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<VideoInfo> queryWrapper = new QueryWrapper<>();
        //排除已删除影视视频信息
        queryWrapper.lambda().ne(VideoInfo::getStatus, 2)
                .orderByDesc(VideoInfo::getVideoInfoId);
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(VideoInfo::getVideoName, vo.getQueryWords());
        }
        if(vo.getObjectId() != null && StringUtils.hasText(vo.getObjectId().toString())){
            try {
                queryWrapper.lambda().eq(VideoInfo::getStatus, Byte.valueOf(vo.getObjectId().toString()));
            }catch (Exception e){
                throw new IllegalArgumentException("状态参数类型错误");
            }
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 根据videoInfoId查询影视视频信息
     */
    @Override
    public VideoInfoVo getVideoInfoByVideoInfoId(String videoInfoId) {
        QueryWrapper<VideoInfo> queryWrapper = new QueryWrapper<>();
        //排除已删除影视视频信息
        queryWrapper.lambda().eq(VideoInfo::getVideoInfoId, videoInfoId)
                .ne(VideoInfo::getStatus, 2);
        VideoInfoVo videoInfoVo = new VideoInfoVo();
        VideoInfo videoInfo = this.baseMapper.selectOne(queryWrapper);
        if(videoInfo == null){
            throw new NoSuchResultException("无此影视视频信息");
        }

        BeanUtil.copyProperties(videoInfo, videoInfoVo);
        //设置分类名
        videoInfoVo.setCategoryName(categoryInfoService.getById(videoInfo.getCategoryId()).getCategoryName());

        return videoInfoVo;
    }

    /**
     * 下架影视视频
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void downVideo(String videoInfoId) throws IOException {
        VideoInfo videoInfo = this.baseMapper.selectById(videoInfoId);
        if(videoInfo == null){
            throw new NoSuchResultException("无此videoInfoId影视信息");
        }

        VideoInfo todoUpdate = new VideoInfo();
        todoUpdate.setVideoInfoId(videoInfoId)
                .setStatus(VideoStatus.DOWN);

        this.baseMapper.updateById(todoUpdate);

        DeleteByQueryResponse response = elasticsearchClient.deleteByQuery(builder ->
                builder.index(Constant.ELASTIC_SEARCH_INDEX)
                        .query(q ->
                                q.term(t ->
                                        t.field("videoInfoId")
                                                .value(videoInfoId))));
        log.info("影视视频下架videoInfoId: " + videoInfoId + "，下架数量" + response.deleted());
    }

    /**
     * 修改影视信息
     */
    @Override
    public void updateVideoInfo(UpdateVideoInfoVo vo) {
        VideoInfo videoInfo = this.baseMapper.selectOne(new QueryWrapper<VideoInfo>().lambda()
                .eq(VideoInfo::getVideoInfoId, vo.getVideoInfoId())
                .ne(VideoInfo::getStatus, VideoStatus.DELETED)
                .ne(VideoInfo::getStatus, VideoStatus.LOGIC_DELETE));
        if(videoInfo == null){
            return;
        }

        VideoInfo todoUpdate = new VideoInfo();
        BeanUtil.copyProperties(vo, todoUpdate);
        if(vo.getFile() != null){
            //远程调用thirdparty微服务上传新封面图
            ResultObject<String> r = ossFeignService.uploadImage(vo.getFile());
            if(!r.getResponseCode().equals(200)){
                throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
            }
            todoUpdate.setVideoImage(r.getResult());
        }
        todoUpdate.setUpdateTime(LocalDateTime.now());

        this.baseMapper.updateById(todoUpdate);
    }
}
