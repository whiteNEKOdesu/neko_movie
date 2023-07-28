package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.VideoCollection;
import org.apache.ibatis.annotations.Mapper;

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

}
