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
 * 订单rabbitmq消息发送失败记录表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Data
@Accessors(chain = true)
@TableName("mq_order_return_message")
public class MqOrderReturnMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * rabbitmq回退消息id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String mqReturnId;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 删除时间
     */
    private LocalDateTime updateTime;
}
