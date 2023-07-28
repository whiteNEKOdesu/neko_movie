package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 秒杀折扣活动vo
 */
@Data
@Accessors(chain = true)
public class DiscountInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀折扣活动id
     */
    private String discountId;

    /**
     * 秒杀折扣名
     */
    private String discountName;

    /**
     * 折扣百分比
     */
    private Integer discountRate;

    /**
     * 限制数量
     */
    private Integer number;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
