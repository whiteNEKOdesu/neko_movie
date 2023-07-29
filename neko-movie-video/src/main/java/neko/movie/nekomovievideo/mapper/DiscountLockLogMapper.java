package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.DiscountLockLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 会员限时折扣限制数量锁定日志表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Mapper
public interface DiscountLockLogMapper extends BaseMapper<DiscountLockLog> {
    void updateStockLockLogStatus(String orderId,
                                  Byte status,
                                  LocalDateTime updateTime);
}
