package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.VideoCollection;
import neko.movie.nekomovievideo.vo.VideoCollectionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Mapper
public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {
    List<VideoCollectionVo> getUserSelfVideoCollectionByQueryLimitedPage(Integer limited,
                                                                         Integer start,
                                                                         String queryWords,
                                                                         String uid);

    int getUserSelfVideoCollectionByQueryLimitedPageNumber(String queryWords,
                                                           String uid);
}
