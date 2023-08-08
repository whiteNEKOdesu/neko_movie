package neko.movie.nekomoviecommonbase.utils.entity;

public class Constant {
    public static final String REDIS_PREFIX = "neko_movie:";

    public static final String MEMBER_REDIS_PREFIX = REDIS_PREFIX + "member:";

    public static final String VIDEO_REDIS_PREFIX = REDIS_PREFIX + "video:";

    public static final String ELASTIC_SEARCH_INDEX = "neko_movie";

    public static final int VIDEO_WATCH_HISTORY_SIZE = 15;
}
