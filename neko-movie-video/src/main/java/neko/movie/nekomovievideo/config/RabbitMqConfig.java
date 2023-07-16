package neko.movie.nekomovievideo.config;

import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 使用JSON序列化机制，进行消息转换
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 订单交换机
     */
    @Bean
    public TopicExchange orderExchange(){
        return ExchangeBuilder.topicExchange(RabbitMqConstant.ORDER_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * 订单处理队列
     */
    @Bean
    public Queue orderHandleStockQueue(){
        return QueueBuilder.durable(RabbitMqConstant.ORDER_HANDLE_QUEUE_NAME)
                .build();
    }

    /**
     * 订单处理死信队列
     */
    @Bean
    public Queue orderHandleDelayQueue(){
        return QueueBuilder.durable(RabbitMqConstant.ORDER_HANDLE_DELAY_QUEUE_NAME)
                .deadLetterExchange(RabbitMqConstant.ORDER_EXCHANGE_NAME)
                .deadLetterRoutingKey(RabbitMqConstant.ORDER_HANDLE_QUEUE_ROUTING_KEY_NAME)
                .ttl(1000 * 60 * 5)
                .build();
    }

    /**
     * 订单处理队列跟库存交换机绑定
     */
    @Bean
    public Binding orderHandleBinding(Queue orderHandleStockQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(orderHandleStockQueue)
                .to(orderExchange)
                .with(RabbitMqConstant.ORDER_HANDLE_QUEUE_ROUTING_KEY_NAME);
    }

    /**
     * 订单处理死信队列跟库存交换机绑定
     */
    @Bean
    public Binding orderHandleDelayQueueBinding(Queue orderHandleDelayQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(orderHandleDelayQueue)
                .to(orderExchange)
                //此routingKey不会发送给任何消费者，只为实现延迟队列
                .with(RabbitMqConstant.ORDER_DEAD_LETTER_ROUTING_KEY_NAME);
    }
}
