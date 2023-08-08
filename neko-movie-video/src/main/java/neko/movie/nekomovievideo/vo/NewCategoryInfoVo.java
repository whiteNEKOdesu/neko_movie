package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class NewCategoryInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer parentId;

    /**
     * 分类层级，0，1，最大为1
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer level;

    /**
     * 分类名
     */
    @NotBlank
    private String categoryName;
}
