package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoWatchHistoryService;
import neko.movie.nekomovievideo.vo.VideoWatchHistoryVo;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        zSetOperations.add(videoSeriesId, System.currentTimeMillis());
        Long size = zSetOperations.size();
        //超出最大记录数删除多余记录
        if(size != null && size > 15){
            zSetOperations.removeRange(0, size - 15 - 1);
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
        Set<String> videoWatchHistorySet = zSetOperations.reverseRangeByScore(0, -1);
        if(videoWatchHistorySet == null){
            return null;
        }

        List<String> videoSeriesIds = new ArrayList<>(videoWatchHistorySet);

        return videoSeriesInfoMapper.getVideoSeriesInfoByVideoInfoIds(videoSeriesIds);
    }
}
