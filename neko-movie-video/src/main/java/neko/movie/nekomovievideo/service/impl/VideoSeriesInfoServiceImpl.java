package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.entity.*;
import neko.movie.nekomoviecommonbase.utils.exception.FileTypeNotSupportException;
import neko.movie.nekomoviecommonbase.utils.exception.MemberServiceException;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.feign.member.MemberLevelDictFeignService;
import neko.movie.nekomovievideo.feign.member.UserWeightFeignService;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import neko.movie.nekomovievideo.service.VideoWatchHistoryService;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import neko.movie.nekomovievideo.to.UserWeightTo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoUserVo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 影视集数信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class VideoSeriesInfoServiceImpl extends ServiceImpl<VideoSeriesInfoMapper, VideoSeriesInfo> implements VideoSeriesInfoService {
    @Resource
    private OSSFeignService ossFeignService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private MemberLevelDictFeignService memberLevelDictFeignService;

    @Resource
    private UserWeightFeignService userWeightFeignService;

    @Resource
    private VideoWatchHistoryService videoWatchHistoryService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
                                   MultipartFile file) throws InterruptedException {
        //获取分布式锁
        RLock lock = redissonClient.getLock(Constant.VIDEO_REDIS_PREFIX + "lock:video_series:" + videoInfoId);
        //尝试加锁，最多等待100秒，上锁以后10分钟自动解锁
        boolean isLock = lock.tryLock(100, 60 * 10, TimeUnit.SECONDS);

        if(isLock){
            try {
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
            }finally {
                lock.unlock();
            }
        }else{
            throw new InterruptedException("获取分布式锁失败");
        }
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

        //添加观看记录
        videoWatchHistoryService.newVideoWatchHistory(videoSeriesId);
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
     * 获取会员等级信息
     */
    private List<MemberLevelDictTo> getLevelInfos(){
        String key = Constant.MEMBER_REDIS_PREFIX + "level_info";
        String cache = stringRedisTemplate.opsForValue().get(key);

        //缓存有数据
        if(cache != null){
            return JSONUtil.toList(JSONUtil.parseArray(cache), MemberLevelDictTo.class);
        }else{
            //获取会员等级信息
            ResultObject<List<MemberLevelDictTo>> r = memberLevelDictFeignService.levelInfos();
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
}
