package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.Response;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.FileTypeNotSupportException;
import neko.movie.nekomoviecommonbase.utils.exception.MemberServiceException;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.feign.member.MemberLevelDictFeignService;
import neko.movie.nekomovievideo.feign.member.UserWeightFeignService;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import neko.movie.nekomovievideo.service.VideoWatchHistoryService;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import neko.movie.nekomovievideo.to.UserWeightTo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoUserVo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 影视集数信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
@Slf4j
public class VideoSeriesInfoServiceImpl extends ServiceImpl<VideoSeriesInfoMapper, VideoSeriesInfo> implements VideoSeriesInfoService {
    @Resource
    private OSSFeignService ossFeignService;

    @Resource
    private MemberLevelDictFeignService memberLevelDictFeignService;

    @Resource
    private UserWeightFeignService userWeightFeignService;

    @Resource
    private VideoWatchHistoryService videoWatchHistoryService;

    @Resource
    private VideoInfoMapper videoInfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource(name = "threadPoolExecutor")
    private Executor threadPool;

    /**
     * 分页查询指定videoSeriesId视频分集信息
     */
    @Override
    public Page<VideoSeriesInfoVo> getVideoSeriesInfoForAdminByQueryLimitedPage(QueryVo vo) throws ExecutionException, InterruptedException {
        if(vo.getObjectId() == null){
            throw new IllegalArgumentException("缺少参数videoSeriesId");
        }

        Page<VideoSeriesInfoVo> page = new Page<>(vo.getCurrentPage(), vo.getLimited());

        CompletableFuture<Void> pageTask = CompletableFuture.runAsync(() -> {
            //分页查询指定videoSeriesId视频分集信息
            page.setRecords(this.baseMapper.getVideoSeriesInfoForAdminByQueryLimitedPage(vo.getLimited(),
                    vo.daoPage(),
                    vo.getQueryWords(),
                    vo.getObjectId().toString()));
            page.setTotal(this.baseMapper.getVideoSeriesInfoForAdminByQueryLimitedPageNumber(vo.getQueryWords(), vo.getObjectId().toString()));
        }, threadPool);

        Map<Integer,String> weightMap = new HashMap<>();

        CompletableFuture<Void> levelTask = CompletableFuture.runAsync(() -> {
            //获取会员等级类型全部权限信息
            List<UserWeightTo> memberLevelWeightInfos = getMemberLevelWeightInfos();

            for(UserWeightTo userWeightTo : memberLevelWeightInfos){
                weightMap.put(userWeightTo.getWeightId(), userWeightTo.getWeightType());
            }
        }, threadPool);

        CompletableFuture.allOf(pageTask, levelTask).get();

        //为vo设置会员等级名
        for(VideoSeriesInfoVo videoSeriesInfoVo : page.getRecords()){
            videoSeriesInfoVo.setWeightType(weightMap.get(videoSeriesInfoVo.getWeightId()));
        }

        return page;
    }

    /**
     * 添加视频分集集数
     */
    @Override
    public void newVideoSeriesInfo(String videoInfoId,
                                   Integer seriesNumber,
                                   Integer weightId,
                                   MultipartFile file) {
        if(this.baseMapper.selectOne(new QueryWrapper<VideoSeriesInfo>().lambda()
                .eq(VideoSeriesInfo::getVideoInfoId, videoInfoId)
                .eq(VideoSeriesInfo::getSeriesNumber, seriesNumber)
                .eq(VideoSeriesInfo::getIsDelete, false)) != null){
            throw new DuplicateKeyException("集数重复");
        }

        //上传视频到oss
        ResultObject<String> r = ossFeignService.uploadVideo(file);
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }else if(r.getResponseCode().equals(Response.FILE_TYPE_NOT_SUPPORT_ERROR.getResponseCode())){
            throw new FileTypeNotSupportException("视频类型错误");
        }

        //获取 oss 上传视频地址
        String videoUrl = r.getResult();
        VideoSeriesInfo videoSeriesInfo = new VideoSeriesInfo();
        LocalDateTime now = LocalDateTime.now();
        videoSeriesInfo.setVideoInfoId(videoInfoId)
                .setSeriesNumber(seriesNumber)
                .setVideoUrl(videoUrl)
                .setWeightId(weightId)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(videoSeriesInfo);
    }

    /**
     * 根据影视集数id获取影视单集信息
     */
    @Override
    public VideoSeriesInfoVo getVideoSeriesInfoByVideoSeriesInfoId(String videoSeriesId) {
        VideoSeriesInfo videoSeriesInfo = this.baseMapper.selectOne(new QueryWrapper<VideoSeriesInfo>().lambda()
                .eq(VideoSeriesInfo::getVideoSeriesId, videoSeriesId)
                .eq(VideoSeriesInfo::getIsDelete, false));
        if(videoSeriesInfo == null){
            throw new NoSuchResultException("无此影视集数信息");
        }

        //获取请求上下文
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //异步处理播放记录，播放量数据
        CompletableFuture.runAsync(() -> {
            //为新线程设置请求上下文
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //添加观看记录
            videoWatchHistoryService.newVideoWatchHistory(videoSeriesId);

            try {
                //记录播放量
                logPlayNumber(videoSeriesInfo.getVideoInfoId());
            }catch (Exception e){
                log.error("记录播放量出现异常，" + e);
            }
        }, threadPool).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        //远程调用member微服务获取影视集数观看要求权限名
        ResultObject<String> r = userWeightFeignService.memberLevelWeightNameByWeightId(videoSeriesInfo.getWeightId());
        if(!r.getResponseCode().equals(200)){
            throw new MemberServiceException("member微服务远程调用异常");
        }

        String weightType = r.getResult();
        //校验是否拥有观看权限
        StpUtil.checkPermission(weightType);

        VideoSeriesInfoVo videoSeriesInfoVo = new VideoSeriesInfoVo();
        BeanUtil.copyProperties(videoSeriesInfo, videoSeriesInfoVo);
        videoSeriesInfoVo.setWeightType(weightType);

        return videoSeriesInfoVo;
    }

    /**
     * 查询指定videoInfoId全部视频分集信息
     */
    @Override
    public List<VideoSeriesInfoUserVo> getVideoSeriesInfosByVideoInfoId(String videoInfoId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<VideoSeriesInfoUserVo>> videoSeriesInfoTask = CompletableFuture.supplyAsync(() ->
                this.baseMapper.getVideoSeriesInfosByVideoInfoId(videoInfoId), threadPool);

        Map<Integer,String> weightMap = new HashMap<>();

        CompletableFuture<Void> levelTask = CompletableFuture.runAsync(() -> {
            //获取会员等级类型全部权限信息
            List<UserWeightTo> memberLevelWeightInfos = getMemberLevelWeightInfos();

            for(UserWeightTo userWeightTo : memberLevelWeightInfos){
                weightMap.put(userWeightTo.getWeightId(), userWeightTo.getWeightType());
            }
        }, threadPool);

        CompletableFuture.allOf(videoSeriesInfoTask, levelTask).get();

        List<VideoSeriesInfoUserVo> list = videoSeriesInfoTask.get();
        //为vo设置会员等级名
        for(VideoSeriesInfoUserVo videoSeriesInfoUserVo : list){
            videoSeriesInfoUserVo.setWeightType(weightMap.get(videoSeriesInfoUserVo.getWeightId()));
        }

        return list;
    }

    /**
     * 删除指定videoSeriesId视频分集信息
     */
    @Override
    public void deleteVideoSeriesInfo(String videoSeriesId) {
        VideoSeriesInfo videoSeriesInfo = this.baseMapper.selectById(videoSeriesId);
        if(videoSeriesInfo == null){
            return;
        }

        //远程调用thirdparty微服务删除视频
        ResultObject<Object> r = ossFeignService.deleteFile(videoSeriesInfo.getVideoUrl());
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        VideoSeriesInfo todoUpdate = new VideoSeriesInfo();
        todoUpdate.setVideoSeriesId(videoSeriesId)
                .setIsDelete(true);

        this.baseMapper.updateById(todoUpdate);
    }

    /**
     * 删除指定所属videoInfoId全部视频分集信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVideoSeriesInfosByVideoInfoId(String videoInfoId) {
        VideoSeriesInfo videoSeriesInfo = new VideoSeriesInfo();
        videoSeriesInfo.setIsDelete(true)
                .setUpdateTime(LocalDateTime.now());

        //修改视频分集信息为删除状态
        this.baseMapper.update(videoSeriesInfo, new UpdateWrapper<VideoSeriesInfo>().lambda()
                .eq(VideoSeriesInfo::getVideoInfoId, videoInfoId));

        //获取全部视频分集信息
        List<VideoSeriesInfo> videoSeriesInfos = this.baseMapper.selectList(new QueryWrapper<VideoSeriesInfo>().lambda()
                .eq(VideoSeriesInfo::getVideoInfoId, videoInfoId));
        //将全部视频分集信息视频地址收集为list
        List<String> videoUrls = videoSeriesInfos.stream().map(VideoSeriesInfo::getVideoUrl)
                .collect(Collectors.toList());
        VideoInfo videoInfo = videoInfoMapper.selectById(videoInfoId);
        if(videoInfo != null && videoInfo.getVideoImage() != null){
            //将封面图添加到删除list
            videoUrls.add(videoInfo.getVideoImage());
        }
        if(!videoUrls.isEmpty()){
            //远程调用thirdparty微服务批量删除视频
            ResultObject<Object> r = ossFeignService.deleteFileBatch(videoUrls);
            if(!r.getResponseCode().equals(200)){
                throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
            }
        }

        log.info("影视视频删除成功，videoInfoId: " + videoInfoId);
    }

    /**
     * 获取会员等级类型全部权限信息
     */
    private List<UserWeightTo> getMemberLevelWeightInfos(){
        String key = Constant.MEMBER_REDIS_PREFIX + "member_level_weight_info";
        String cache = stringRedisTemplate.opsForValue().get(key);

        //缓存有数据
        if(cache != null){
            return JSONUtil.toList(JSONUtil.parseArray(cache), UserWeightTo.class);
        }else{
            //远程调用member微服务获取会员等级类型权限信息
            ResultObject<List<UserWeightTo>> r = userWeightFeignService.memberLevelWeightInfos();
            if(!r.getResponseCode().equals(200)){
                throw new MemberServiceException("member微服务远程调用异常");
            }

            //缓存无数据，查询存入缓存
            stringRedisTemplate.opsForValue().setIfAbsent(key,
                    JSONUtil.toJsonStr(r.getResult()),
                    1000 * 60 * 60 * 5,
                    TimeUnit.MILLISECONDS);

            return r.getResult();
        }
    }

    /**
     * 记录播放量
     */
    private void logPlayNumber(String videoInfoId) throws IOException {
        String key = Constant.MEMBER_REDIS_PREFIX + "play_number:" + videoInfoId;
        Long playNumber = stringRedisTemplate.opsForValue().increment(key);
        if(playNumber == null){
            stringRedisTemplate.opsForValue().setIfAbsent(key,
                    "1",
                    30,
                    TimeUnit.DAYS);
            return;
        }

        stringRedisTemplate.expire(key,
                30,
                TimeUnit.DAYS);
        if(playNumber >= 10){
            //修改播放量
            videoInfoMapper.increasePlayNumber(videoInfoId, playNumber, LocalDateTime.now());
            //将redis中播放量设为0
            stringRedisTemplate.opsForValue().set(key,
                    "0",
                    30,
                    TimeUnit.DAYS);

            //获取影视信息数据
            VideoInfo videoInfo = videoInfoMapper.selectById(videoInfoId);
            if(videoInfo != null){
                VideoInfoES videoInfoES = new VideoInfoES();
                videoInfoES.setPlayNumber(videoInfo.getPlayNumber());
                //修改elasticsearch中影视信息数据
                UpdateResponse<VideoInfoES> response = elasticsearchClient.update(builder ->
                        builder.index(Constant.ELASTIC_SEARCH_INDEX)
                                .id(videoInfoId)
                                .doc(videoInfoES)
                                //表示修改而不是覆盖
                                .docAsUpsert(true), VideoInfoES.class);
                log.info("修改elasticsearch中影视数据，videoInfoId: " + videoInfoId + "，修改数量: " + response.shards().successful().intValue());
            }
        }
    }
}
