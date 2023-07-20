package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 影视集数信息表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface VideoSeriesInfoService extends IService<VideoSeriesInfo> {
    Page<VideoSeriesInfo> getVideoSeriesInfoForAdminByQueryLimitedPage(QueryVo vo);

    void newVideoSeriesInfo(String videoInfoId,
                            Integer seriesNumber,
                            Integer requireLevel,
                            MultipartFile file);
}
