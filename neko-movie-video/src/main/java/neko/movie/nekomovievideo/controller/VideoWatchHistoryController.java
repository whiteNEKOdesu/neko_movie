package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.VideoWatchHistoryService;
import neko.movie.nekomovievideo.vo.VideoWatchHistoryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 影视观看记录前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("video_watch_history")
public class VideoWatchHistoryController {
    @Resource
    private VideoWatchHistoryService videoWatchHistoryService;

    /**
     * 获取用户观看记录
     */
    @SaCheckLogin
    @GetMapping("watch_history_infos")
    public ResultObject<List<VideoWatchHistoryVo>> watchHistoryInfos(){
        return ResultObject.ok(videoWatchHistoryService.videoWatchHistoryInfos());
    }
}
