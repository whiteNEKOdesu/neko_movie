package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.VideoInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视信息表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface VideoInfoService extends IService<VideoInfo> {
    void newVideoInfo(String videoName,
                      String videoDescription,
                      String videoProducer,
                      String videoActors,
                      LocalDateTime upTime,
                      MultipartFile file);

    void upVideo(String videoInfoId) throws IOException;

    Page<VideoInfo> getVideoInfoByQueryLimitedPage(QueryVo vo);
}
