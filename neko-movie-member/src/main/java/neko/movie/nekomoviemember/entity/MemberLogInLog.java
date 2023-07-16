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
 * 用户登录记录表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("member_log_in_log")
public class MemberLogInLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String logInId;

    private String uid;

    private String ip;

    private Boolean isLogIn;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
