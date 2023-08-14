package neko.movie.nekomovievideo.listener;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.OrderStatus;
import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import neko.movie.nekomoviecommonbase.utils.exception.RabbitMQMessageRejectException;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.service.OrderInfoService;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * rabbitmq订单处理延迟队列监听
 */
@Component
@RabbitListener(queues = RabbitMqConstant.ORDER_HANDLE_QUEUE_NAME)
@Slf4j
public class OrderListener {
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private DiscountInfoService discountInfoService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void StockUnlock(String jsonMessage, Message message, Channel channel) throws IOException {
        RabbitMQMessageTo<String> rabbitMQMessageTo = JSONUtil.toBean(jsonMessage,
                new TypeReference<RabbitMQMessageTo<String>>() {},
                true);
        String orderId = rabbitMQMessageTo.getMessage();
        try {
            OrderInfo orderInfo = orderInfoService.getById(orderId);
            if(orderInfo == null){
                log.warn("解锁库存订单号: " + orderId + "，订单不存在，尝试解锁库存");
                //解锁库存
                discountInfoService.unlockStock(orderId);
                //单个确认消费消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            //订单不为未支付状态，无需取消订单
            if(!orderInfo.getStatus().equals(OrderStatus.UNPAID)){
                //单个确认消费消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }

            log.info("订单超时准备验证关闭，订单号: " + orderId);
            //修改订单状态为取消状态
            orderInfoService.updateOrderInfoStatusToCancel(orderId);

            if(orderInfo.getDiscountId() != null){
                //解锁库存
                discountInfoService.unlockStock(orderId);
            }

            //单个确认消费消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("订单关闭发生异常，订单号: " + orderId);
            //拒收消息，并让消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            throw new RabbitMQMessageRejectException("订单关闭发生异常");
        }
    }
}
