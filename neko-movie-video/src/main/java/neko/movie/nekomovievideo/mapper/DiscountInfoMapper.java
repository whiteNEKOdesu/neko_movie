package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 秒杀折扣信息表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Mapper
public interface DiscountInfoMapper extends BaseMapper<DiscountInfo> {

}
