package neko.movie.nekomoviecommonbase.utils.entity;

public class RabbitMqConstant {
    //订单交换机名
    public static final String ORDER_EXCHANGE_NAME = "neko-movie-order-exchange";

    //订单处理队列名
    public static final String ORDER_HANDLE_QUEUE_NAME = "neko.movie.order.handle.queue";

    //订单处理延迟队列名
    public static final String ORDER_HANDLE_DELAY_QUEUE_NAME = "neko.movie.order.handle.delay.queue";

    //订单处理队列routingKey名
    public static final String ORDER_HANDLE_QUEUE_ROUTING_KEY_NAME = "neko.movie.order.handle.#";

    //订单处理死信队列routingKey名
    public static final String ORDER_DEAD_LETTER_ROUTING_KEY_NAME = "neko.movie.order.delay";
}
