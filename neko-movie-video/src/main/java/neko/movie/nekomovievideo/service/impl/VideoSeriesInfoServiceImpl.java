package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.Response;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.FileTypeNotSupportException;
import neko.movie.nekomoviecommonbase.utils.exception.MemberServiceException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.feign.member.MemberLevelDictFeignService;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
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

        Map<Integer,String> levelMap = new HashMap<>();

        CompletableFuture<Void> levelTask = CompletableFuture.runAsync(() -> {
            //获取会员等级信息
            ResultObject<List<MemberLevelDictTo>> r = memberLevelDictFeignService.levelInfos();
            if(!r.getResponseCode().equals(200)){
                throw new MemberServiceException("member微服务调用异常");
            }

            for(MemberLevelDictTo memberLevelDictTo : r.getResult()){
                levelMap.put(memberLevelDictTo.getMemberLevelId(), memberLevelDictTo.getLevelName());
            }
        }, threadPool);

        CompletableFuture.allOf(pageTask, levelTask).get();

        //为vo设置会员等级名
        for(VideoSeriesInfoVo videoSeriesInfoVo : page.getRecords()){
            videoSeriesInfoVo.setLevelName(levelMap.get(videoSeriesInfoVo.getRequireMemberLevelId()));
        }

        return page;
    }

    /**
     * 添加视频分集集数
     */
    @Override
    public void newVideoSeriesInfo(String videoInfoId,
                                   Integer seriesNumber,
                                   Integer requireMemberLevelId,
                                   MultipartFile file) throws InterruptedException {
        //获取分布式锁
        RLock lock = redissonClient.getLock(Constant.VIDEO_REDIS_PREFIX + "lock:video_series:" + videoInfoId);
        //尝试加锁，最多等待100秒，上锁以后10分钟自动解锁
        boolean isLock = lock.tryLock(100, 60 * 10, TimeUnit.SECONDS);

        if(isLock){
            try {
                if(this.baseMapper.selectOne(new QueryWrapper<VideoSeriesInfo>().lambda()
                        .eq(VideoSeriesInfo::getVideoInfoId, videoInfoId)
                        .eq(VideoSeriesInfo::getSeriesNumber, seriesNumber)) != null){
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
                        .setRequireMemberLevelId(requireMemberLevelId)
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
}
