package neko.movie.nekomoviemember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户等级字典表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("member_level_dict")
public class MemberLevelDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "member_level_id", type = IdType.AUTO)
    private Integer memberLevelId;

    /**
     * 角色id，对应user_role表role_id
     */
    private Integer roleId;

    /**
     * 开通价格/月
     */
    private BigDecimal price;

    /**
     * 等级排序，最低0
     */
    private Integer level;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
