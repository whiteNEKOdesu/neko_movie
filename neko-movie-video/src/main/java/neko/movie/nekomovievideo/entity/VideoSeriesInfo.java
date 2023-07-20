package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视集数信息表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("video_series_info")
public class VideoSeriesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
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
     * 观看所需等级id，对应neko_movie_member数据库member_level_dict表member_level_id
     */
    private Integer requireMemberLevelId;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
