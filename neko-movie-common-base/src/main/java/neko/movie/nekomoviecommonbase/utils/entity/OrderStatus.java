package neko.movie.nekomoviecommonbase.utils.entity;

/**
 * 订单状态
 */
public class OrderStatus {
    /**
     * 取消
     */
    public static final Byte CANCELED = -1;

    /**
     * 未支付
     */
    public static final Byte UNPAID = 0;

    /**
     * 已支付
     */
    public static final Byte PAID = 1;
}
