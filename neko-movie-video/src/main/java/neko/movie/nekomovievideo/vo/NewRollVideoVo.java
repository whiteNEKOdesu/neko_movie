package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加轮播图vo
 */
@Data
@Accessors(chain = true)
public class NewRollVideoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 轮播图描述
     */
    @NotBlank
    private String rollDescription;

    /**
     * 影视信息id，对应video_info表video_info_id
     */
    @NotBlank
    private String videoInfoId;

    /**
     * 轮播图
     */
    @NotNull
    private MultipartFile file;

    /**
     * 排序字段，越小越靠前
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 15)
    private Integer sort;
}
