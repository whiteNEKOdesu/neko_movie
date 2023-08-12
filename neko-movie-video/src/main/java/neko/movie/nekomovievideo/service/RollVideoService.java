package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.entity.RollVideo;
import neko.movie.nekomovievideo.vo.NewRollVideoVo;

import java.util.List;

/**
 * <p>
 * 影视信息轮播图表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-08-12
 */
public interface RollVideoService extends IService<RollVideo> {
    void newRollVideo(NewRollVideoVo vo);

    List<RollVideo> getRollVideos();

    void deleteRollVideo(Integer rollId);
}
