package neko.movie.nekomoviecommonbase.utils.entity;

/**
 * 影视状态
 */
public class VideoStatus {
    /**
     * 下架
     */
    public static final Byte DOWN = -1;

    /**
     * 上架
     */
    public static final Byte UP = 0;

    /**
     * 回收站
     */
    public static final Byte LOGIC_DELETE = 1;

    /**
     * 删除
     */
    public static final Byte DELETED = 2;
}
