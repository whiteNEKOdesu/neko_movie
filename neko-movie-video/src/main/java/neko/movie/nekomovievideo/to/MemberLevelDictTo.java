package neko.movie.nekomovievideo.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MemberLevelDictTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memberLevelId;

    /**
     * 用户等级
     */
    private Integer memberLevel;

    /**
     * 等级名
     */
    private String levelName;

    /**
     * 开通价格/月
     */
    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
