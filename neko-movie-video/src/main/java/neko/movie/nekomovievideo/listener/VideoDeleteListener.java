package neko.movie.nekomovievideo.listener;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviecommonbase.utils.exception.RabbitMQMessageRejectException;
import neko.movie.nekomovievideo.service.VideoInfoService;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * rabbitmq视频删除延迟队列监听
 */
@Component
@RabbitListener(queues = RabbitMqConstant.VIDEO_DELETE_QUEUE_NAME)
@Slf4j
public class VideoDeleteListener {
    @Resource
    private VideoInfoService videoInfoService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void deleteVideo(String jsonMessage, Message message, Channel channel) throws IOException {
        RabbitMQMessageTo<String> rabbitMQMessageTo = JSONUtil.toBean(jsonMessage,
                new TypeReference<RabbitMQMessageTo<String>>() {},
                true);
        String videoInfoId = rabbitMQMessageTo.getMessage();
        try {
            //删除影视信息
            videoInfoService.deleteVideoInfo(videoInfoId);

            log.info("影视信息回收站到期删除删除成功，videoInfoId: " + videoInfoId);
            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("影视信息回收站到期删除发生异常，videoInfoId: " + videoInfoId);
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            throw new RabbitMQMessageRejectException("影视信息回收站到期删除发生异常");
        }
    }
}
