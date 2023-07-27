package neko.movie.nekomoviemember.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class MemberInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String uid;

    private String userName;

    /**
     * 社交登录名
     */
    private String sourceName;

    private String userImagePath;

    private List<String> weightTypes;

    private List<String> roleTypes;

    private String memberLevelRoleType;

    private String token;

    private Byte gender;

    /**
     * 社交登录来源
     */
    private String source;

    private String sourceUid;

    private String realName;

    private String idCardNumber;

    private String phone;

    private String mail;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户积分
     */
    private Integer point;

    private Boolean isBan;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
