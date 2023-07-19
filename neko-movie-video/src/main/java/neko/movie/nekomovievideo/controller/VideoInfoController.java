package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.service.VideoInfoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视信息表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("video_info")
public class VideoInfoController {
    @Resource
    private VideoInfoService videoInfoService;

    /**
     * 添加影视信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_video_info")
    public ResultObject<Object> newVideoInfo(@RequestParam String videoName,
                                             @RequestParam String videoDescription,
                                             @RequestParam String videoProducer,
                                             @RequestParam String videoActors,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam LocalDateTime upTime,
                                             @RequestPart MultipartFile file){
        videoInfoService.newVideoInfo(videoName,
                videoDescription,
                videoProducer,
                videoActors,
                upTime,
                file);

        return ResultObject.ok();
    }

    /**
     * 上架影视视频
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("up_video")
    public ResultObject<Object> upVideo(@RequestParam String videoInfoId) throws IOException {
        videoInfoService.upVideo(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 分页查询影视视频信息
     */
    @PostMapping("video_infos")
    public ResultObject<Page<VideoInfo>> videoInfos(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(videoInfoService.getVideoInfoByQueryLimitedPage(vo));
    }
}
