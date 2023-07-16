package neko.movie.nekomovievideo.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class WeightRoleRelationTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer relationId;

    private Integer weightId;

    private String weightType;

    private Integer roleId;

    /**
     * 角色类型，冗余字段
     */
    private String roleType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
