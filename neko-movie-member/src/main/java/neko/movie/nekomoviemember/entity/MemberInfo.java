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
 * 用户信息表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("member_info")
public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String uid;

    private String userName;

    private String userPassword;

    private String salt;

    /**
     * 社交登录名
     */
    private String sourceName;

    private String userImagePath;

    private Byte gender;

    /**
     * 社交登录来源
     */
    private String source;

    /**
     * 社交登录来源uid
     */
    private String sourceUid;

    private String realName;

    private String idCardNumber;

    private String idCardImage;

    private String phone;

    private String mail;

    private Boolean isBan;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
