package neko.movie.nekomovievideo.mapper;

import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoUserVo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import neko.movie.nekomovievideo.vo.VideoWatchHistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 影视集数信息表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface VideoSeriesInfoMapper extends BaseMapper<VideoSeriesInfo> {
    List<VideoSeriesInfoVo> getVideoSeriesInfoForAdminByQueryLimitedPage(Integer limited,
                                                                         Integer start,
                                                                         String queryWords,
                                                                         String videoInfoId);

    int getVideoSeriesInfoForAdminByQueryLimitedPageNumber(String queryWords, String videoInfoId);

    List<VideoSeriesInfoUserVo> getVideoSeriesInfosByVideoInfoId(String videoInfoId);

    List<VideoWatchHistoryVo> getVideoSeriesInfoByVideoInfoIds(List<String> videoInfoIds);
}
