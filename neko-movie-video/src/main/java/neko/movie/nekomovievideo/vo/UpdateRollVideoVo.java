package neko.movie.nekomovievideo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改轮播图vo
 */
@Data
@Accessors(chain = true)
public class UpdateRollVideoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 轮播图id
     */
    @NotNull
    private Integer rollId;

    /**
     * 轮播图描述
     */
    private String rollDescription;

    /**
     * 轮播图
     */
    private MultipartFile file;

    /**
     * 排序字段，越小越靠前
     */
    @Min(value = 0)
    @Max(value = 15)
    private Integer sort;
}
