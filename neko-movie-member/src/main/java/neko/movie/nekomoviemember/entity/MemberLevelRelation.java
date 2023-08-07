package neko.movie.nekomoviemember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户，会员等级关系表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Data
@Accessors(chain = true)
@TableName("member_level_relation")
public class MemberLevelRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String relationId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 会员等级id，对应member_level_dict表member_level_id
     */
    private Integer memberLevelId;

    /**
     * 等级到期时间
     */
    private LocalDateTime levelExpireTime;

    /**
     * 乐观锁
     */
    private Integer updateVersion;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
