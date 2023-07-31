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
 * rabbitmq消息发送失败记录表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Data
@Accessors(chain = true)
@TableName("mq_return_message")
public class MqReturnMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * rabbitmq回退消息id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String mqReturnId;

    /**
     * 消息
     */
    private String message;

    /**
     * 消息类型，0->订单处理延迟队列消息，1->视频删除延迟队列消息，2->修改会员等级消息
     */
    private Byte type;

    /**
     * 是否删除
     */
    private Byte isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
