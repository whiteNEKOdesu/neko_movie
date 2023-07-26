package neko.movie.nekomoviemember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleType;

    /**
     * 角色类型种类，0->普通角色类型，1->管理员角色类型，2->会员等级角色类型
     */
    private Byte type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
