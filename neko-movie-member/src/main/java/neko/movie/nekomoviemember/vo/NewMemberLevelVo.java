package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class NewMemberLevelVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户等级
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer memberLevel;

    /**
     * 等级名
     */
    @NotBlank
    private String levelName;

    /**
     * 达到此等级最低需要积分
     */
    @NotNull
    @Min(value = 0)
    private Integer achievePoint;

    /**
     * 成长积分
     */
    @NotNull
    @Min(value = 0)
    private Integer growPoint;

    /**
     * 折扣百分比
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Double discount;
}
