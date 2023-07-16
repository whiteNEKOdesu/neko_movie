package neko.movie.nekomovievideo.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RabbitMQOrderMessageTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderRecord;

    /**
     * 消息类型，0->解锁库存延迟队列消息，1->支付确认扣减库存消息
     */
    private Byte type;
}
