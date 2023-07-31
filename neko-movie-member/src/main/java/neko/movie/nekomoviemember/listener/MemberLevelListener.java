package neko.movie.nekomoviemember.listener;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviemember.service.MemberLevelRelationService;
import neko.movie.nekomoviemember.to.RabbitMQMessageTo;
import neko.movie.nekomoviemember.to.UpdateMemberLevelTo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * rabbitmq修改会员等级队列监听
 */
@Component
@RabbitListener(queues = RabbitMqConstant.MEMBER_LEVEL_UPDATE_QUEUE_NAME)
@Slf4j
public class MemberLevelListener {
    @Resource
    private MemberLevelRelationService memberLevelRelationService;

    @RabbitHandler
    public void memberLevelUpdate(String jsonMessage, Message message, Channel channel) throws IOException {
        RabbitMQMessageTo<UpdateMemberLevelTo> rabbitMQMessageTo = JSONUtil.toBean(jsonMessage,
                new TypeReference<RabbitMQMessageTo<UpdateMemberLevelTo>>() {},
                true);
        UpdateMemberLevelTo updateMemberLevelTo = rabbitMQMessageTo.getMessage();
        try {
            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId() + "，开始处理");
            //修改用户等级信息
            memberLevelRelationService.newMemberLevelRelation(updateMemberLevelTo.getUid(),
                    updateMemberLevelTo.getMemberLevelId(),
                    updateMemberLevelTo.getPayLevelMonths());

            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId() + "，开通成功");
            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("开通会员等级发生异常，uid: " + updateMemberLevelTo.getUid() + "，开通会员等级id: " + updateMemberLevelTo.getMemberLevelId());
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
