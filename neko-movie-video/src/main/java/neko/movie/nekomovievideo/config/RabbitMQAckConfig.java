package neko.movie.nekomovievideo.config;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.MQMessageType;
import neko.movie.nekomoviecommonbase.utils.exception.RabbitMQSendException;
import neko.movie.nekomovievideo.service.MqReturnMessageService;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import neko.movie.nekomovievideo.to.UpdateMemberLevelTo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class RabbitMQAckConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MqReturnMessageService mqReturnMessageService;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(!ack){
                //ConfirmCallback中回退消息需要在发送消息设置在 CorrelationData 中，如果不设置，需要判断是否为空
                if(correlationData != null && correlationData.getReturned() != null){
                    String message = new String(correlationData.getReturned().getMessage().getBody(), StandardCharsets.UTF_8);
                    RabbitMQMessageTo<Object> rabbitMQMessageTo = JSONUtil.toBean(message,
                            new TypeReference<RabbitMQMessageTo<Object>>() {},
                            true);

                    if(MQMessageType.ORDER_STATUS_CHECK_TYPE.equals(rabbitMQMessageTo.getType())){
                        RabbitMQMessageTo<String> mqTo = JSONUtil.toBean(message,
                                new TypeReference<RabbitMQMessageTo<String>>() {},
                                true);
                        log.error("订单处理延迟队列消息发送至交换机失败，订单id: " + mqTo.getMessage() + "，原因: " + cause);
                    }else if(MQMessageType.VIDEO_DELETE_TYPE.equals(rabbitMQMessageTo.getType())){
                        RabbitMQMessageTo<String> mqTo = JSONUtil.toBean(message,
                                new TypeReference<RabbitMQMessageTo<String>>() {},
                                true);
                        log.error("视频删除延迟队列消息发送至交换机失败，videoInfoId: " + mqTo.getMessage() + "，原因: " + cause);
                    }else if(MQMessageType.MEMBER_LEVEL_UPDATE_TYPE.equals(rabbitMQMessageTo.getType())){
                        RabbitMQMessageTo<UpdateMemberLevelTo> mqTo = JSONUtil.toBean(message,
                                new TypeReference<RabbitMQMessageTo<UpdateMemberLevelTo>>() {},
                                true);
                        log.error("修改会员等级队列消息发送至交换机失败，uid: " + mqTo.getMessage().getUid() +
                                "，开通会员等级id: " + mqTo.getMessage().getMemberLevelId() + "，原因: " + cause);
                    }

                    //记录发送失败消息
                    mqReturnMessageService.logReturnMessage(rabbitMQMessageTo, message);
                }
                throw new RabbitMQSendException("消息发送失败");
            }
        });

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            String message = new String(returnedMessage.getMessage().getBody(), StandardCharsets.UTF_8);
            //rabbitmq中ReturnsCallback回退 json 数据在 "" 中，需要去掉 ""，并且需要替换转义字符 \
            message = message.substring(1)
                    .replaceAll("\\\\", "");
            RabbitMQMessageTo<Object> rabbitMQMessageTo = JSONUtil.toBean(message,
                    new TypeReference<RabbitMQMessageTo<Object>>() {},
                    true);

            if(MQMessageType.ORDER_STATUS_CHECK_TYPE.equals(rabbitMQMessageTo.getType())){
                RabbitMQMessageTo<String> mqTo = JSONUtil.toBean(message,
                        new TypeReference<RabbitMQMessageTo<String>>() {},
                        true);
                log.error("订单处理延迟队列消息被交换机: " + returnedMessage.getExchange() + " 回退，订单id: "
                        + mqTo.getMessage() + "，replyCode: " +
                        returnedMessage.getReplyCode() + "，replyText: " +
                        returnedMessage.getReplyText() + "，routingKey: " +
                        returnedMessage.getRoutingKey());
            }else if(MQMessageType.VIDEO_DELETE_TYPE.equals(rabbitMQMessageTo.getType())){
                RabbitMQMessageTo<String> mqTo = JSONUtil.toBean(message,
                        new TypeReference<RabbitMQMessageTo<String>>() {},
                        true);
                log.error("视频删除延迟队列消息被交换机: " + returnedMessage.getExchange() + " 回退，videoInfoId: "
                        + mqTo.getMessage() + "，replyCode: " +
                        returnedMessage.getReplyCode() + "，replyText: " +
                        returnedMessage.getReplyText() + "，routingKey: " +
                        returnedMessage.getRoutingKey());
            }else if(MQMessageType.MEMBER_LEVEL_UPDATE_TYPE.equals(rabbitMQMessageTo.getType())){
                RabbitMQMessageTo<UpdateMemberLevelTo> mqTo = JSONUtil.toBean(message,
                        new TypeReference<RabbitMQMessageTo<UpdateMemberLevelTo>>() {},
                        true);
                log.error("修改会员等级队列消息被交换机: " + returnedMessage.getExchange() + " 回退，uid: "
                        + mqTo.getMessage().getUid() + "，开通会员等级id: " + mqTo.getMessage().getMemberLevelId() + "，replyCode: " +
                        returnedMessage.getReplyCode() + "，replyText: " +
                        returnedMessage.getReplyText() + "，routingKey: " +
                        returnedMessage.getRoutingKey());
            }

            //记录发送失败消息
            mqReturnMessageService.logReturnMessage(rabbitMQMessageTo, message);
        });
    }
}
