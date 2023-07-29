package neko.movie.nekomoviemember.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RabbitMQMessageTo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T message;

    /**
     * 消息类型，0->订单处理延迟队列消息，1->视频删除延迟队列消息，2->修改会员等级队列消息
     */
    private Byte type;

    public static <T> RabbitMQMessageTo<T> generateMessage(T message, Byte type){
        return new RabbitMQMessageTo<T>()
                .setMessage(message)
                .setType(type);
    }
}
