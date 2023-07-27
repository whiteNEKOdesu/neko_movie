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
public class NewUserRoleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @NotBlank
    private String roleType;

    /**
     * 角色类型种类，0->普通角色类型，1->管理员角色类型
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Byte type;
}
