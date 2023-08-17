package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.entity.MqReturnMessage;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;

import java.util.List;

/**
 * <p>
 * rabbitmq消息发送失败记录表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
public interface MqReturnMessageService extends IService<MqReturnMessage> {
    void logReturnMessage(RabbitMQMessageTo<Object> to, String jsonMessage);

    void deleteMqReturnMessageByMqReturnIds(List<String> mqReturnIds);
}
