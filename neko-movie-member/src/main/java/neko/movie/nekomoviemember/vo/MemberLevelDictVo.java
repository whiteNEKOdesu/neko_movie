package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MemberLevelDictVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memberLevelId;

    /**
     * 角色id，对应user_role表role_id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleType;

    /**
     * 开通价格/月
     */
    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
