package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * elasticsearch影视信息分类聚合饼图vo
 */
@Data
@Accessors(chain = true)
public class VideoCategoryAggPieVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long value;

    private String name;
}
