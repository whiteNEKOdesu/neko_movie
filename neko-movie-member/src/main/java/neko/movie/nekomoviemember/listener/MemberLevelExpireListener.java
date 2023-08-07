package neko.movie.nekomoviemember.listener;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviemember.service.MemberLevelRelationService;
import neko.movie.nekomoviemember.to.MemberLevelExpireTo;
import neko.movie.nekomoviemember.to.RabbitMQMessageTo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * rabbitmq会员等级过期插件延迟队列监听
 */
@Component
@RabbitListener(queues = RabbitMqConstant.MEMBER_LEVEL_EXPIRE_QUEUE_NAME)
@Slf4j
public class MemberLevelExpireListener {
    @Resource
    private MemberLevelRelationService memberLevelRelationService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void memberLevelExpireHandler(String jsonMessage, Message message, Channel channel) throws IOException {
        RabbitMQMessageTo<MemberLevelExpireTo> rabbitMQMessageTo = JSONUtil.toBean(jsonMessage,
                new TypeReference<RabbitMQMessageTo<MemberLevelExpireTo>>() {},
                true);
        MemberLevelExpireTo memberLevelExpireTo = rabbitMQMessageTo.getMessage();
        try {
            //删除相关会员权益
            memberLevelRelationService.expireMemberLevel(memberLevelExpireTo);

            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("订单关闭发生异常，relationId: " + memberLevelExpireTo.getRelationId());
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
