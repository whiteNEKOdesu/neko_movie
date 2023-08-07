package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏信息vo
 */
@Data
@Accessors(chain = true)
public class VideoCollectionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    private String collectionId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 收藏影视信息id，对应video_info表video_info_id
     */
    private String videoInfoId;

    /**
     * 剧名
     */
    private String videoName;

    /**
     * 介绍信息
     */
    private String videoDescription;

    private String videoImage;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
