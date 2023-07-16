package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UpdateMemberLevelVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer memberLevelId;

    /**
     * 等级名
     */
    private String levelName;

    /**
     * 达到此等级最低需要积分
     */
    @Min(value = 0)
    private Integer achievePoint;

    /**
     * 成长积分
     */
    @Min(value = 0)
    private Integer growPoint;

    /**
     * 折扣百分比
     */
    @Min(value = 1)
    @Max(value = 100)
    private Double discount;

    public boolean isEmpty(){
        return !StringUtils.hasText(levelName) && achievePoint == null &&
                growPoint == null && discount == null;
    }
}
