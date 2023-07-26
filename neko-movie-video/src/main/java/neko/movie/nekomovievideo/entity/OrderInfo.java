package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String orderId;

    /**
     * 支付宝流水id
     */
    private String alipayTradeId;

    private String uid;

    /**
     * 秒杀折扣id，对应discount_info表discount_id
     */
    private String discountId;

    /**
     * 订单总价
     */
    private BigDecimal cost;

    /**
     * 实际支付价格
     */
    private BigDecimal actualCost;

    /**
     * -1->取消，0->未支付，1->已支付
     */
    private Byte status;

    /**
     * 订单购买等级id
     */
    private Integer memberLevelId;

    /**
     * 订单购买会员等级类型角色名，冗余字段
     */
    private String roleType;

    /**
     * 开通月数
     */
    private Integer payLevelMonths;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
