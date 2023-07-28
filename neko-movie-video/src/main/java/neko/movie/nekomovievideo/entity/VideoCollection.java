package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户收藏表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Data
@Accessors(chain = true)
@TableName("video_collection")
public class VideoCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String collectionId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 收藏影视信息id，对应video_info表video_info_id
     */
    private String videoInfoId;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
