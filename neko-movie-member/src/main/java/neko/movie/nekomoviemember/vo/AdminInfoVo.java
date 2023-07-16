package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class AdminInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String adminId;

    private String userName;

    private String userImagePath;

    private String token;

    private List<String> weightTypes;

    private List<String> roleTypes;

    /**
     * 指认管理员id
     */
    private String operateAdminId;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
