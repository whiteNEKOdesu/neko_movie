package neko.movie.nekomovievideo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员添加秒杀折扣活动
 */
@Data
@Accessors(chain = true)
public class NewDiscountInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀折扣名
     */
    @NotBlank
    private String discountName;

    /**
     * 折扣百分比
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Double discountRate;

    /**
     * 限制数量
     */
    @NotNull
    @Min(value = 1)
    private Integer number;

    /**
     * 开始时间，必须晚于添加时间2天
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}
