package neko.movie.nekomoviemember.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MemberLevelInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roleType;

    /**
     * 等级到期时间
     */
    private LocalDateTime levelExpireTime;
}
