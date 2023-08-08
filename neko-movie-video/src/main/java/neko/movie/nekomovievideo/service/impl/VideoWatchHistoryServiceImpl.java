package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoWatchHistoryService;
import neko.movie.nekomovievideo.vo.VideoWatchHistoryVo;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 影视观看记录服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class VideoWatchHistoryServiceImpl implements VideoWatchHistoryService {
    @Resource
    private VideoSeriesInfoMapper videoSeriesInfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加观看记录
     */
    @Override
    public void newVideoWatchHistory(String videoSeriesId) {
        String key = Constant.VIDEO_REDIS_PREFIX + "watch_history:" + StpUtil.getLoginId();
        BoundZSetOperations<String, String> zSetOperations = stringRedisTemplate.boundZSetOps(key);
        //获取集数信息
        VideoWatchHistoryVo videoWatchHistoryVo = videoSeriesInfoMapper.getVideoWatchHistoryVoByVideoInfoId(videoSeriesId);
        zSetOperations.add(JSONUtil.toJsonStr(videoWatchHistoryVo), System.currentTimeMillis());
        Long size = zSetOperations.size();
        //超出最大记录数删除多余记录
        if(size != null && size > Constant.VIDEO_WATCH_HISTORY_SIZE){
            zSetOperations.removeRange(0, size - Constant.VIDEO_WATCH_HISTORY_SIZE - 1);
        }

        zSetOperations.expire(30, TimeUnit.DAYS);
    }

    /**
     * 获取用户观看记录
     */
    @Override
    public List<VideoWatchHistoryVo> videoWatchHistoryInfos() {
        String key = Constant.VIDEO_REDIS_PREFIX + "watch_history:" + StpUtil.getLoginId();
        BoundZSetOperations<String, String> zSetOperations = stringRedisTemplate.boundZSetOps(key);
        Set<ZSetOperations.TypedTuple<String>> videoWatchHistorySet = zSetOperations.reverseRangeWithScores(0, Constant.VIDEO_WATCH_HISTORY_SIZE - 1);
        if(videoWatchHistorySet == null || videoWatchHistorySet.isEmpty()){
            return null;
        }

        List<VideoWatchHistoryVo> videoWatchHistoryVos = new ArrayList<>();
        for(ZSetOperations.TypedTuple<String> set : videoWatchHistorySet){
            videoWatchHistoryVos.add(JSONUtil.toBean(set.getValue(), VideoWatchHistoryVo.class));
        }

        return videoWatchHistoryVos;
    }
}
