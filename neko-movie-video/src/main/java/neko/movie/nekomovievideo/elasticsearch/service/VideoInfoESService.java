package neko.movie.nekomovievideo.elasticsearch.service;

import neko.movie.nekomovievideo.vo.VideoCategoryAggVo;
import neko.movie.nekomovievideo.vo.VideoInfoESQueryVo;
import neko.movie.nekomovievideo.vo.VideoInfoESVo;

import java.io.IOException;

public interface VideoInfoESService {
    VideoInfoESVo getVideoInfoByQueryLimitedPage(VideoInfoESQueryVo vo) throws IOException;

    VideoCategoryAggVo videoCategoryAgg() throws IOException;
}
