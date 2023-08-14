package neko.movie.nekomoviemember.listener;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.MQMessageType;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviecommonbase.utils.exception.RabbitMQMessageRejectException;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import neko.movie.nekomoviemember.service.MemberLevelRelationService;
import neko.movie.nekomoviemember.to.MemberLevelExpireTo;
import neko.movie.nekomoviemember.to.RabbitMQMessageTo;
import neko.movie.nekomoviemember.to.UpdateMemberLevelTo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * rabbitmq修改会员等级队列监听
 */
@Component
@RabbitListener(queues = RabbitMqConstant.MEMBER_LEVEL_UPDATE_QUEUE_NAME)
@Slf4j
public class MemberLevelListener {
    @Resource
    private MemberLevelRelationService memberLevelRelationService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void memberLevelUpdate(String jsonMessage, Message message, Channel channel) throws IOException {
        RabbitMQMessageTo<UpdateMemberLevelTo> rabbitMQMessageTo = JSONUtil.toBean(jsonMessage,
                new TypeReference<RabbitMQMessageTo<UpdateMemberLevelTo>>() {},
                true);
        UpdateMemberLevelTo updateMemberLevelTo = rabbitMQMessageTo.getMessage();
        try {
            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId() + "，开始处理");
            //修改用户等级信息
            MemberLevelRelation memberLevelRelation = memberLevelRelationService.newMemberLevelRelation(updateMemberLevelTo.getUid(),
                    updateMemberLevelTo.getMemberLevelId(),
                    updateMemberLevelTo.getPayLevelMonths());
            if(memberLevelRelation != null){
                //组装会员等级过期插件延迟队列消息to
                MemberLevelExpireTo memberLevelExpireTo = new MemberLevelExpireTo();
                memberLevelExpireTo.setRelationId(memberLevelRelation.getRelationId())
                        .setUpdateVersion(memberLevelRelation.getUpdateVersion());

                RabbitMQMessageTo<MemberLevelExpireTo> memberLevelExpireRabbitMQMessageTo = RabbitMQMessageTo.generateMessage(memberLevelExpireTo,
                        MQMessageType.MEMBER_LEVEL_EXPIRE_TYPE);
                //在CorrelationData中设置回退消息
                CorrelationData correlationData = new CorrelationData(MQMessageType.MEMBER_LEVEL_EXPIRE_TYPE.toString());
                jsonMessage = JSONUtil.toJsonStr(memberLevelExpireRabbitMQMessageTo);
                String notAvailable = "not available";
                correlationData.setReturned(new ReturnedMessage(new Message(jsonMessage.getBytes(StandardCharsets.UTF_8)),
                        0,
                        notAvailable,
                        notAvailable,
                        notAvailable));
                //向会员等级过期插件延迟队列发送消息，用于实现会员过期
                rabbitTemplate.convertAndSend(RabbitMqConstant.MEMBER_LEVEL_EXPIRE_EXCHANGE_NAME,
                        RabbitMqConstant.MEMBER_LEVEL_EXPIRE_QUEUE_ROUTING_KEY_NAME,
                        jsonMessage,
                        (messagePostProcessor) -> {
                            messagePostProcessor.getMessageProperties()
                                    .setHeader("x-delay",
                                            Duration.between(memberLevelRelation.getCreateTime(),
                                                    memberLevelRelation.getLevelExpireTime()).toMillis());
                            return messagePostProcessor;
                        },
                        correlationData);
            }

            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId() + "，开通成功");
            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("开通会员等级发生异常，uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId());
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            throw new RabbitMQMessageRejectException("开通会员等级发生异常");
        }
    }
}
