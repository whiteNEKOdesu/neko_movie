package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class NewWeightRoleRelationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer roleId;

    @NotEmpty
    private List<Integer> weightIds;
}
