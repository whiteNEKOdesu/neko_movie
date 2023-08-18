package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.vo.DiscountInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

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

    int lockStock(String discountId,
                  Integer lockNumber,
                  LocalDateTime updateTime);

    void decreaseStock(String discountId,
                       Integer lockNumber,
                       LocalDateTime updateTime);

    void unlockStock(String discountId,
                    String orderId,
                    LocalDateTime updateTime);

    int expireDiscountInfo(List<String> discountIds, LocalDateTime updateTime);
}
