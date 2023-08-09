package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoUserVo;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 影视集数信息表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface VideoSeriesInfoService extends IService<VideoSeriesInfo> {
    Page<VideoSeriesInfoVo> getVideoSeriesInfoForAdminByQueryLimitedPage(QueryVo vo) throws ExecutionException, InterruptedException;

    void newVideoSeriesInfo(String videoInfoId,
                            Integer seriesNumber,
                            Integer weightId,
                            MultipartFile file) throws InterruptedException;

    VideoSeriesInfoVo getVideoSeriesInfoByVideoSeriesInfoId(String videoSeriesId);

    List<VideoSeriesInfoUserVo> getVideoSeriesInfosByVideoInfoId(String videoInfoId) throws ExecutionException, InterruptedException;

    void deleteVideoSeriesInfo(String videoSeriesId);

    void deleteVideoSeriesInfosByVideoInfoId(String videoInfoId);
}
