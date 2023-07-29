package neko.movie.nekomoviemember.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UpdateMemberLevelTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;

    /**
     * 订单购买等级id
     */
    private Integer memberLevelId;

    /**
     * 开通月数
     */
    private Integer payLevelMonths;
}
