package neko.movie.nekomovievideo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视信息表
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Data
@Accessors(chain = true)
@TableName("video_info")
public class VideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String videoInfoId;

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
    private LocalDateTime upTime;

    /**
     * -1->下架，0->上架，1->回收站，2->删除
     */
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
