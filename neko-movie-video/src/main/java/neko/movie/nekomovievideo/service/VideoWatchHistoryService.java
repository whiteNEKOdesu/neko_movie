package neko.movie.nekomovievideo.service;

import neko.movie.nekomovievideo.vo.VideoWatchHistoryVo;

import java.util.List;

/**
 * <p>
 * 影视观看记录服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface VideoWatchHistoryService {
    void newVideoWatchHistory(String videoSeriesId);

    List<VideoWatchHistoryVo> videoWatchHistoryInfos();
}
