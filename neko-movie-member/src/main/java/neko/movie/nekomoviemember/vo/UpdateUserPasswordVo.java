package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UpdateUserPasswordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @NotBlank
    private String userPassword;

    /**
     * 新密码
     */
    @NotBlank
    private String todoPassword;
}
