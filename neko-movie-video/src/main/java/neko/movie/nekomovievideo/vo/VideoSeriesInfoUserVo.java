package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class VideoSeriesInfoUserVo implements Serializable {
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
     * 观看所需等级id，对应neko_movie_member数据库member_level_dict表member_level_id
     */
    private Integer requireMemberLevelId;

    /**
     * 观看所需等级名
     */
    private String levelName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
