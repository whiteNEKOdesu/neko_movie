package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LogInVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;
}
