package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * elasticsearch影视信息分类聚合vo
 */
@Data
@Accessors(chain = true)
public class VideoCategoryAggVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * elasticsearch影视信息分类聚合饼图vo
     */
    private List<PieVo> pieVos;

    /**
     * elasticsearch影视信息分类聚合柱状图vo
     */
    private List<BarVo> barVos;

    @Data
    @Accessors(chain = true)
    public static class PieVo {
        private Long value;

        private String name;
    }

    @Data
    @Accessors(chain = true)
    public static class BarVo {
        private Long value;

        private String name;
    }
}
