package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 提交订单vo
 */
@Data
@Accessors(chain = true)
public class NewOrderInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 提交订单接口幂等性保证token
     */
    @NotBlank
    private String token;

    /**
     * 秒杀折扣id，对应discount_info表discount_id
     */
    private String discountId;

    /**
     * 订单购买等级id
     */
    @NotNull
    private Integer memberLevelId;

    /**
     * 开通月数
     */
    @NotNull
    private Integer payLevelMonths;
}
