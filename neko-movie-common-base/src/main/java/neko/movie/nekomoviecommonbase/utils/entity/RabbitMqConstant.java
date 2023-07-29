package neko.movie.nekomoviecommonbase.utils.entity;

public class RabbitMqConstant {
    //---------------------------------------------------------------------------------------------------
    /**
     * 订单处理延迟队列常量配置
     */
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

    //---------------------------------------------------------------------------------------------------
    /**
     * 视频删除延迟队列常量配置
     */
    //视频删除交换机名
    public static final String VIDEO_DELETE_EXCHANGE_NAME = "neko-movie-video-delete-exchange";

    //视频删除队列名
    public static final String VIDEO_DELETE_QUEUE_NAME = "neko.movie.video.delete.queue";

    //视频删除延迟队列名
    public static final String VIDEO_DELETE_DELAY_QUEUE_NAME = "neko.movie.video.delete.delay.queue";

    //视频删除队列routingKey名
    public static final String VIDEO_DELETE_QUEUE_ROUTING_KEY_NAME = "neko.movie.video.delete.#";

    //视频删除死信队列routingKey名
    public static final String VIDEO_DELETE_DEAD_LETTER_ROUTING_KEY_NAME = "neko.movie.video.delay";

    //---------------------------------------------------------------------------------------------------
    /**
     * 修改会员等级队列常量配置
     */
    //修改会员等级交换机名
    public static final String MEMBER_LEVEL_UPDATE_EXCHANGE_NAME = "neko-movie-member-level-update-exchange";

    //修改会员等级队列名
    public static final String MEMBER_LEVEL_UPDATE_QUEUE_NAME = "neko.movie.member.level.update.queue";

    //修改会员等级队列routingKey名
    public static final String MEMBER_LEVEL_UPDATE_QUEUE_ROUTING_KEY_NAME = "neko.movie.member.level.update.#";
}
