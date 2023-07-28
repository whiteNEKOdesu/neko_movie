package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.vo.DiscountInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

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
    DiscountInfoVo getDiscountInfoNearTwoDaysOrAvailable();

    int lockStock(String discountId, LocalDateTime updateTime);
}
