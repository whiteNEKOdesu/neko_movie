package neko.movie.nekomovievideo.mapper;

import neko.movie.nekomovievideo.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    void updateOrderInfoStatusToCancel(String orderId,
                                      LocalDateTime updateTime);
}
