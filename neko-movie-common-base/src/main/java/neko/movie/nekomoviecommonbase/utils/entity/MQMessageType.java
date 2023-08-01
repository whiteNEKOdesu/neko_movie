package neko.movie.nekomoviecommonbase.utils.entity;

public class MQMessageType {
    /**
     * 订单处理延迟队列消息
     */
    public static final Byte ORDER_STATUS_CHECK_TYPE = 0;

    /**
     * 视频删除延迟队列消息
     */
    public static final Byte VIDEO_DELETE_TYPE = 1;

    /**
     * 修改会员等级队列消息
     */
    public static final Byte MEMBER_LEVEL_UPDATE_TYPE = 2;

    /**
     * 会员等级过期插件延迟队列消息
     */
    public static final Byte MEMBER_LEVEL_EXPIRE_TYPE = 3;
}
