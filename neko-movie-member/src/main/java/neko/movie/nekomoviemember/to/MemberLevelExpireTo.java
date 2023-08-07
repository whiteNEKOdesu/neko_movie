package neko.movie.nekomoviemember.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 会员等级过期rabbitmq消息to
 */
@Data
@Accessors(chain = true)
public class MemberLevelExpireTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    private String relationId;

    /**
     * 乐观锁
     */
    private Integer updateVersion;
}
