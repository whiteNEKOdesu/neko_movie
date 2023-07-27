package neko.movie.nekomovievideo.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserWeightTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer weightId;

    /**
     * 权限名
     */
    private String weightType;

    /**
     * 权限类型种类，0->普通权限，1->会员等级权限
     */
    private Byte type;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
