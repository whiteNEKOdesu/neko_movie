package neko.movie.nekomoviecommonbase.utils.entity;

/**
 * 库存锁定状态
 */
public class StockStatus {
    /**
     * 已取消锁定
     */
    public static Byte CANCEL_LOCK = -1;

    /**
     * 锁定中
     */
    public static Byte LOCKING = 0;

    /**
     * 用户已支付
     */
    public static Byte PAY = 1;
}
