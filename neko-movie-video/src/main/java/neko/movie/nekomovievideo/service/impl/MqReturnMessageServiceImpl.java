package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.MqReturnMessage;
import neko.movie.nekomovievideo.mapper.MqReturnMessageMapper;
import neko.movie.nekomovievideo.service.MqReturnMessageService;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * rabbitmq消息发送失败记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Service
public class MqReturnMessageServiceImpl extends ServiceImpl<MqReturnMessageMapper, MqReturnMessage> implements MqReturnMessageService {

    /**
     * 记录发送失败消息
     */
    @Override
    public void logReturnMessage(RabbitMQMessageTo<Object> to, String jsonMessage) {
        MqReturnMessage mqReturnMessage = new MqReturnMessage();
        LocalDateTime now = LocalDateTime.now();
        mqReturnMessage.setMessage(jsonMessage)
                .setType(to.getType())
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(mqReturnMessage);
    }

    /**
     * 根据mqReturnId list删除订单rabbitmq消息发送失败记录
     */
    @Override
    public void deleteMqReturnMessageByMqReturnIds(List<String> mqReturnIds) {
        this.baseMapper.deleteMqReturnMessageByMqReturnIds(mqReturnIds, LocalDateTime.now());
    }
}
