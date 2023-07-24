package neko.movie.nekomovievideo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * elasticsearch查询vo
 */
@Data
@Accessors(chain = true)
public class VideoInfoESQueryVo implements Serializable {
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

    private String queryWords;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime minTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime maxTime;

    @NotNull
    @Min(value = 0)
    @Max(value = 50)
    private Integer currentPage;

    @NotNull
    @Min(value = 3)
    @Max(value = 50)
    private Integer limited;

    public Integer getFrom(){
        return (currentPage - 1) * limited;
    }
}
