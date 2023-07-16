package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class NewAdminInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;

    /**
     * 角色id
     */
    @NotEmpty
    private List<Integer> roleIds;
}
