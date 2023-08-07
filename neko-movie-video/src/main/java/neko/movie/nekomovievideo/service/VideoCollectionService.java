package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.VideoCollection;
import neko.movie.nekomovievideo.vo.VideoCollectionVo;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
public interface VideoCollectionService extends IService<VideoCollection> {
    void newVideoCollection(String videoInfoId);

    void deleteVideoCollection(String collectionId);

    Page<VideoCollectionVo> getUserSelfVideoCollectionByQueryLimitedPage(QueryVo vo);
}
