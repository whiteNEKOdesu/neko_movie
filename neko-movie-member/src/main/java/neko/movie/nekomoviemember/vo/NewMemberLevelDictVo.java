package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class NewMemberLevelDictVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @NotBlank
    private String roleType;

    /**
     * 开通价格/月
     */
    @NotNull
    private BigDecimal price;
}
