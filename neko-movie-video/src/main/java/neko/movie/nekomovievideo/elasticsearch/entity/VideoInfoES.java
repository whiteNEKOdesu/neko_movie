package neko.movie.nekomovievideo.elasticsearch.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class VideoInfoES implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private Integer categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 导演
     */
    private String videoProducer;

    /**
     * 演员信息
     */
    private String videoActors;

    /**
     * 上映时间
     */
    private String upTime;

    /**
     * 总播放量
     */
    private Long playNumber;
}
