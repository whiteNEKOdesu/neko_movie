package neko.movie.nekomovievideo.mapper;

import neko.movie.nekomovievideo.entity.VideoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 影视信息表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface VideoInfoMapper extends BaseMapper<VideoInfo> {

}
