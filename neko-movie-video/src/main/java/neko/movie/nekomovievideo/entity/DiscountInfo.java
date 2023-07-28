package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀折扣信息表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Data
@Accessors(chain = true)
@TableName("discount_info")
public class DiscountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秒杀折扣活动id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String discountId;

    /**
     * 秒杀折扣名
     */
    private String discountName;

    /**
     * 折扣百分比
     */
    private Integer discountRate;

    /**
     * 添加活动管理员id
     */
    private String operateAdminUid;

    /**
     * 限制数量
     */
    private Integer number;

    /**
     * 锁定数量
     */
    private Integer lockNumber;

    /**
     * 是否结束
     */
    private Boolean isEnd;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
