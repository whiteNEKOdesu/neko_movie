package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员限时折扣限制数量锁定日志表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Data
@Accessors(chain = true)
@TableName("discount_lock_log")
public class DiscountLockLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId(type = IdType.INPUT)
    private String orderId;

    /**
     * 秒杀折扣活动id，对应discount_info表discount_id
     */
    private String discountId;

    /**
     * 锁定数量
     */
    private Integer lockNumber;

    /**
     * -1->已取消锁定，0->锁定中，1->用户已支付
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
