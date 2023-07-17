package neko.movie.nekomovievideo.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.VideoInfoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
}
