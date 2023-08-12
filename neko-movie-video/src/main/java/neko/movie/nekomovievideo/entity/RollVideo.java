package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 影视信息轮播图表
 * </p>
 *
 * @author NEKO
 * @since 2023-08-12
 */
@Data
@Accessors(chain = true)
@TableName("roll_video")
public class RollVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图id
     */
    @TableId(value = "roll_id", type = IdType.AUTO)
    private Integer rollId;

    /**
     * 轮播图描述
     */
    private String rollDescription;

    /**
     * 影视信息id，对应video_info表video_info_id
     */
    private String videoInfoId;

    /**
     * 轮播图地址
     */
    private String rollImage;

    /**
     * 排序字段，越小越靠前
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
