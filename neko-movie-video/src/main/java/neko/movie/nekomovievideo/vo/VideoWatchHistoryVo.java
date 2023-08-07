package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class VideoWatchHistoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 影视信息id，对应video_info表video_info_id
     */
    private String videoInfoId;

    private String videoSeriesId;

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
     * 视频集数
     */
    private Integer seriesNumber;
}
