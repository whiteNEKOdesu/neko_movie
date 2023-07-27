package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class VideoSeriesInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String videoSeriesId;

    /**
     * 影视信息id，对应video_info表video_info_id
     */
    private String videoInfoId;

    /**
     * 视频集数
     */
    private Integer seriesNumber;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 观看所需会员等级类型权限id，对应neko_movie_member数据库user_weight表weight_id
     */
    private Integer weightId;

    /**
     * 观看所需权限名
     */
    private String weightType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
