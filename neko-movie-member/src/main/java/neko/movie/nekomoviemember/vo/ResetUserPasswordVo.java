package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResetUserPasswordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank
    private String userName;

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

    /**
     * 验证码
     */
    @NotBlank
    private String code;
}
