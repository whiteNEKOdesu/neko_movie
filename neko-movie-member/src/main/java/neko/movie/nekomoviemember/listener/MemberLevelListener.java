package neko.movie.nekomoviemember.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviemember.service.MemberInfoService;
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
    private MemberInfoService memberInfoService;

    @RabbitHandler
    public void memberLevelUpdate(RabbitMQMessageTo<UpdateMemberLevelTo> rabbitMQMessageTo, Message message, Channel channel) throws IOException {
        UpdateMemberLevelTo updateMemberLevelTo = rabbitMQMessageTo.getMessage();
        try {
            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级: " + updateMemberLevelTo.getMemberLevelId() + "，开始处理");
            //修改用户等级信息
            memberInfoService.updateMemberLevel(updateMemberLevelTo.getUid(),
                    updateMemberLevelTo.getPayLevelMonths(),
                    updateMemberLevelTo.getMemberLevelId());

            log.info("uid: " + updateMemberLevelTo.getUid() + "，开通会员等级: " + updateMemberLevelTo.getMemberLevelId() + "，开通成功");
            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("开通会员等级发生异常，uid: " + updateMemberLevelTo.getUid() + "，开通会员等级: " + updateMemberLevelTo.getMemberLevelId());
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
