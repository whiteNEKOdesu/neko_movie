package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class VideoInfoVo implements Serializable {
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

    /**
     * 导演
     */
    private String videoProducer;

    /**
     * 演员信息
     */
    private String videoActors;

    private Integer categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 上映时间
     */
    private LocalDateTime upTime;

    /**
     * -1->下架，0->上架，1->回收站，2->删除
     */
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
