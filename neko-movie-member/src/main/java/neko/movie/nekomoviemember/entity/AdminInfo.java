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
 * 管理员表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("admin_info")
public class AdminInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String adminId;

    private String userName;

    private String userPassword;

    private String salt;

    private String userImagePath;

    /**
     * 指认管理员id
     */
    private String operateAdminId;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
