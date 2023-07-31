package neko.movie.nekomoviemember.config;

import neko.movie.nekomoviecommonbase.utils.entity.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {
    /**
     * 使用JSON序列化机制，进行消息转换
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    //---------------------------------------------------------------------------------------------------
    /**
     * 订单处理延迟队列配置
     * <br/>
     * ---------------------------------------------------------------------------------------------------
     * <br/>
     * <br/>
     * <br/>
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

    //---------------------------------------------------------------------------------------------------
    /**
     * 视频删除延迟队列配置
     * <br/>
     * ---------------------------------------------------------------------------------------------------
     * <br/>
     * <br/>
     * <br/>
     * 视频删除交换机
     */
    @Bean
    public TopicExchange videoDeleteExchange(){
        return ExchangeBuilder.topicExchange(RabbitMqConstant.VIDEO_DELETE_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * 视频删除队列
     */
    @Bean
    public Queue videoDeleteQueue(){
        return QueueBuilder.durable(RabbitMqConstant.VIDEO_DELETE_QUEUE_NAME)
                .build();
    }

    /**
     * 视频删除死信队列
     */
    @Bean
    public Queue videoDeleteDelayQueue(){
        return QueueBuilder.durable(RabbitMqConstant.VIDEO_DELETE_DELAY_QUEUE_NAME)
                .deadLetterExchange(RabbitMqConstant.VIDEO_DELETE_EXCHANGE_NAME)
                .deadLetterRoutingKey(RabbitMqConstant.VIDEO_DELETE_QUEUE_ROUTING_KEY_NAME)
                .withArgument("x-message-ttl", 1000 * 60 * 60 * 24 * 15)
                .build();
    }

    /**
     * 视频删除队列跟视频删除交换机绑定
     */
    @Bean
    public Binding videoDeleteBinding(Queue videoDeleteQueue, TopicExchange videoDeleteExchange){
        return BindingBuilder.bind(videoDeleteQueue)
                .to(videoDeleteExchange)
                .with(RabbitMqConstant.VIDEO_DELETE_QUEUE_ROUTING_KEY_NAME);
    }

    /**
     * 视频删除死信队列跟视频删除交换机绑定
     */
    @Bean
    public Binding videoDeleteDelayQueueBinding(Queue videoDeleteDelayQueue, TopicExchange videoDeleteExchange){
        return BindingBuilder.bind(videoDeleteDelayQueue)
                .to(videoDeleteExchange)
                //此routingKey不会发送给任何消费者，只为实现延迟队列
                .with(RabbitMqConstant.VIDEO_DELETE_DEAD_LETTER_ROUTING_KEY_NAME);
    }

    //---------------------------------------------------------------------------------------------------
    /**
     * 修改会员等级队列队列配置
     * <br/>
     * ---------------------------------------------------------------------------------------------------
     * <br/>
     * <br/>
     * <br/>
     * 修改会员等级交换机
     */
    @Bean
    public TopicExchange memberLevelUpdateExchange(){
        return ExchangeBuilder.topicExchange(RabbitMqConstant.MEMBER_LEVEL_UPDATE_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * 修改会员等级队列
     */
    @Bean
    public Queue memberLevelUpdateQueue(){
        return QueueBuilder.durable(RabbitMqConstant.MEMBER_LEVEL_UPDATE_QUEUE_NAME)
                .build();
    }

    /**
     * 修改会员等级队列跟修改会员等级交换机绑定
     */
    @Bean
    public Binding memberLevelUpdateBinding(Queue memberLevelUpdateQueue, TopicExchange memberLevelUpdateExchange){
        return BindingBuilder.bind(memberLevelUpdateQueue)
                .to(memberLevelUpdateExchange)
                .with(RabbitMqConstant.MEMBER_LEVEL_UPDATE_QUEUE_ROUTING_KEY_NAME);
    }

    //---------------------------------------------------------------------------------------------------
    /**
     * 会员等级过期插件延迟队列配置
     * <br/>
     * ---------------------------------------------------------------------------------------------------
     * <br/>
     * <br/>
     * <br/>
     * 会员等级过期交换机
     */
    @Bean
    public CustomExchange memberLevelExpireExchange(){
        Map<String,Object> map = new HashMap<>();
        //设置延迟类型为direct
        map.put("x-delayed-type", "direct");

        //自定义类型交换机，1 交换机名，2 交换机类型，3 是否持久化，4 是否自动删除，5 其他参数
        return new CustomExchange(RabbitMqConstant.MEMBER_LEVEL_EXPIRE_EXCHANGE_NAME,
                RabbitMqConstant.MEMBER_LEVEL_EXPIRE_EXCHANGE_TYPE,
                true,
                false,
                map);
    }

    /**
     * 会员等级过期插件延迟队列
     */
    @Bean
    public Queue memberLevelExpireQueue(){
        return QueueBuilder.durable(RabbitMqConstant.MEMBER_LEVEL_EXPIRE_QUEUE_NAME)
                .build();
    }

    /**
     * 会员等级过期插件延迟队列跟会员等级过期交换机绑定
     */
    @Bean
    public Binding memberLevelExpireBinding(Queue memberLevelExpireQueue, CustomExchange memberLevelExpireExchange){
        return BindingBuilder.bind(memberLevelExpireQueue)
                .to(memberLevelExpireExchange)
                //绑定routingKey
                .with(RabbitMqConstant.MEMBER_LEVEL_EXPIRE_QUEUE_ROUTING_KEY_NAME)
                //自定义类型交换机绑定跟自带类型交换机不同，需要再使用noargs()构建
                .noargs();
    }
}
