package neko.movie.nekomovievideo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 修改影视信息vo
 */
@Data
@Accessors(chain = true)
public class UpdateVideoInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String videoInfoId;

    /**
     * 剧名
     */
    private String videoName;

    /**
     * 介绍信息
     */
    private String videoDescription;

    /**
     * 导演
     */
    private String videoProducer;

    /**
     * 演员信息
     */
    private String videoActors;

    private Integer categoryId;

    /**
     * 上映时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime upTime;

    private MultipartFile file;
}
