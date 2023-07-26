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
 * 权限表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("user_weight")
public class UserWeight implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "weight_id", type = IdType.AUTO)
    private Integer weightId;

    /**
     * 权限名
     */
    private String weightType;

    /**
     * 权限类型种类，0->普通权限，1->会员等级权限
     */
    private Byte type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
