package neko.movie.nekomovievideo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.*;
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
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import neko.movie.nekomovievideo.vo.UpdateVideoInfoVo;
import neko.movie.nekomovievideo.vo.VideoInfoVo;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private VideoSeriesInfoService videoSeriesInfoService;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RabbitTemplate rabbitTemplate;

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
        if(videoInfo.getStatus().equals(VideoStatus.LOGIC_DELETE) || videoInfo.getStatus().equals(VideoStatus.DELETED)){
            return;
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
        if(videoInfo.getStatus().equals(VideoStatus.LOGIC_DELETE) || videoInfo.getStatus().equals(VideoStatus.DELETED)){
            return;
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
    @Transactional(rollbackFor = Exception.class)
    public void updateVideoInfo(UpdateVideoInfoVo vo) throws IOException {
        VideoInfo videoInfo = this.baseMapper.selectOne(new QueryWrapper<VideoInfo>().lambda()
                .eq(VideoInfo::getVideoInfoId, vo.getVideoInfoId())
                .ne(VideoInfo::getStatus, VideoStatus.DELETED)
                .ne(VideoInfo::getStatus, VideoStatus.LOGIC_DELETE));
        if(videoInfo == null){
            return;
        }
        VideoInfoES videoInfoES = new VideoInfoES();
        if(vo.getCategoryId() != null){
            //获取分类信息
            CategoryInfo categoryInfo = categoryInfoService.getById(vo.getCategoryId());
            if(categoryInfo == null){
                return;
            }
            videoInfoES.setCategoryName(categoryInfo.getCategoryName());
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

        //影视视频处于上架状态，则修改elasticsearch中影视信息数据
        if(videoInfo.getStatus().equals(VideoStatus.UP)){
            //将影视视频信息收集为elasticsearch形式
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            BeanUtil.copyProperties(todoUpdate, videoInfoES);
            if(vo.getUpTime() != null){
                videoInfoES.setUpTime(vo.getUpTime().format(dateTimeFormatter));
            }

            //修改elasticsearch中影视信息数据
            UpdateResponse<VideoInfoES> response = elasticsearchClient.update(builder ->
                    builder.index(Constant.ELASTIC_SEARCH_INDEX)
                            .id(vo.getVideoInfoId())
                            .doc(videoInfoES)
                            //表示修改而不是覆盖
                            .docAsUpsert(true), VideoInfoES.class);
            log.info("修改elasticsearch中影视数据，videoInfoId: " + vo.getVideoInfoId() + "，修改数量: " + response.shards().successful().intValue());
        }
    }

    /**
     * 将指定影视信息放入回收站中
     */
    @Override
    public void addVideoInfoToRecycleBin(String videoInfoId) throws IOException {
        VideoInfo videoInfo = this.baseMapper.selectOne(new QueryWrapper<VideoInfo>().lambda()
                .eq(VideoInfo::getVideoInfoId, videoInfoId)
                .ne(VideoInfo::getStatus, VideoStatus.DELETED)
                .ne(VideoInfo::getStatus, VideoStatus.LOGIC_DELETE));

        if(videoInfo == null){
            return;
        }

        RabbitMQMessageTo<String> rabbitMQMessageTo = RabbitMQMessageTo.generateMessage(videoInfoId, MQMessageType.VIDEO_DELETE_TYPE);
        //在CorrelationData中设置回退消息
        CorrelationData correlationData = new CorrelationData(MQMessageType.VIDEO_DELETE_TYPE.toString());
        String jsonMessage = JSONUtil.toJsonStr(rabbitMQMessageTo);
        String notAvailable = "not available";
        correlationData.setReturned(new ReturnedMessage(new Message(jsonMessage.getBytes(StandardCharsets.UTF_8)),
                0,
                notAvailable,
                notAvailable,
                notAvailable));
        //向延迟队列发送videoInfoId，用于回收站自动删除影视视频
        rabbitTemplate.convertAndSend(RabbitMqConstant.VIDEO_DELETE_EXCHANGE_NAME,
                RabbitMqConstant.VIDEO_DELETE_DEAD_LETTER_ROUTING_KEY_NAME,
                jsonMessage,
                correlationData);

        VideoInfo todoUpdate = new VideoInfo();
        todoUpdate.setVideoInfoId(videoInfoId)
                .setDeleteExpireTime(LocalDateTime.now().plusDays(15))
                .setStatus(VideoStatus.LOGIC_DELETE);

        //修改影视信息状态为 回收站 状态
        this.baseMapper.updateById(todoUpdate);

        //删除elasticsearch中数据
        elasticsearchClient.deleteByQuery(builder ->
                builder.index(Constant.ELASTIC_SEARCH_INDEX)
                        .query(q ->
                                q.term(t ->
                                        t.field("videoInfoId")
                                                .value(videoInfoId))));
    }

    /**
     * 将指定影视信息删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVideoInfo(String videoInfoId) throws IOException {
        VideoInfo videoInfo = new VideoInfo();
        LocalDateTime now = LocalDateTime.now();
        videoInfo.setStatus(VideoStatus.DELETED)
                .setUpdateTime(now);

        //修改影视信息状态为删除状态
        if(this.baseMapper.update(videoInfo, new UpdateWrapper<VideoInfo>().lambda()
                .eq(VideoInfo::getVideoInfoId, videoInfoId)
                .eq(VideoInfo::getStatus, VideoStatus.LOGIC_DELETE)) != 1){
            return;
        }

        //删除全部视频分集信息
        videoSeriesInfoService.deleteVideoSeriesInfosByVideoInfoId(videoInfoId);

        //删除elasticsearch中数据
        elasticsearchClient.deleteByQuery(builder ->
                builder.index(Constant.ELASTIC_SEARCH_INDEX)
                        .query(q ->
                                q.term(t ->
                                        t.field("videoInfoId")
                                                .value(videoInfoId))));
    }

    /**
     * 将指定影视信息直接删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVideoInfoDirectly(String videoInfoId) throws IOException {
        VideoInfo videoInfo = new VideoInfo();
        LocalDateTime now = LocalDateTime.now();
        videoInfo.setStatus(VideoStatus.DELETED)
                .setUpdateTime(now);

        //修改影视信息状态为删除状态
        if(this.baseMapper.update(videoInfo, new UpdateWrapper<VideoInfo>().lambda()
                .eq(VideoInfo::getVideoInfoId, videoInfoId)) != 1){
            return;
        }

        //删除全部视频分集信息
        videoSeriesInfoService.deleteVideoSeriesInfosByVideoInfoId(videoInfoId);

        //删除elasticsearch中数据
        elasticsearchClient.deleteByQuery(builder ->
                builder.index(Constant.ELASTIC_SEARCH_INDEX)
                        .query(q ->
                                q.term(t ->
                                        t.field("videoInfoId")
                                                .value(videoInfoId))));
    }

    /**
     * 根据根分类id获取少量热门影视信息
     */
    @Override
    public List<VideoInfoES> getVideoInfoByRootCategoryId(Integer categoryId) {
        return this.baseMapper.getVideoInfoByRootCategoryId(categoryId, 10);
    }

    /**
     * 根据根分类id获取大量热门影视信息
     */
    @Override
    public List<VideoInfoES> getPlentyVideoInfoByRootCategoryId(Integer categoryId) {
        return this.baseMapper.getVideoInfoByRootCategoryId(categoryId, 20);
    }
}
