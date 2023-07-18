package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;

import java.io.Serializable;
import java.util.List;

/**
 * elasticsearch查询结果vo
 */
@Data
@Accessors(chain = true)
public class VideoInfoESVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<VideoInfoES> records;

    private Integer total;

    private Integer size;

    private Integer current;
}
