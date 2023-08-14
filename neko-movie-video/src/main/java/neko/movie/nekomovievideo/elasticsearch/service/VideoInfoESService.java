package neko.movie.nekomovievideo.elasticsearch.service;

import neko.movie.nekomovievideo.vo.VideoCategoryAggPieVo;
import neko.movie.nekomovievideo.vo.VideoInfoESQueryVo;
import neko.movie.nekomovievideo.vo.VideoInfoESVo;

import java.io.IOException;
import java.util.List;

public interface VideoInfoESService {
    VideoInfoESVo getVideoInfoByQueryLimitedPage(VideoInfoESQueryVo vo) throws IOException;

    List<VideoCategoryAggPieVo> videoCategoryAggPie() throws IOException;
}
