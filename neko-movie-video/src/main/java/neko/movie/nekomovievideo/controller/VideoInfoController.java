package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.service.VideoInfoService;
import neko.movie.nekomovievideo.vo.UpdateVideoInfoVo;
import neko.movie.nekomovievideo.vo.VideoInfoVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
     * 管理员添加影视信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_video_info")
    public ResultObject<Object> newVideoInfo(@RequestParam String videoName,
                                             @RequestParam String videoDescription,
                                             @RequestParam String videoProducer,
                                             @RequestParam String videoActors,
                                             @RequestParam Integer categoryId,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam LocalDateTime upTime,
                                             @RequestPart MultipartFile file) {
        videoInfoService.newVideoInfo(videoName,
                videoDescription,
                videoProducer,
                videoActors,
                categoryId,
                upTime,
                file);

        return ResultObject.ok();
    }

    /**
     * 管理员上架影视视频
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("up_video")
    public ResultObject<Object> upVideo(@RequestParam String videoInfoId) throws IOException {
        videoInfoService.upVideo(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 管理员分页查询影视视频信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("video_infos")
    public ResultObject<Page<VideoInfo>> videoInfos(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(videoInfoService.getVideoInfoByQueryLimitedPage(vo));
    }

    /**
     * 根据videoInfoId查询影视视频信息
     */
    @GetMapping("video_info_by_video_info_id")
    public ResultObject<VideoInfoVo> videoInfoByVideoInfoId(@RequestParam String videoInfoId){
        return ResultObject.ok(videoInfoService.getVideoInfoByVideoInfoId(videoInfoId));
    }

    /**
     * 管理员下架影视视频
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("down_video")
    public ResultObject<Object> downVideo(@RequestParam String videoInfoId) throws IOException {
        videoInfoService.downVideo(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 管理员修改影视信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("update_video_info")
    public ResultObject<Object> updateVideoInfo(@Validated UpdateVideoInfoVo vo) throws IOException {
        videoInfoService.updateVideoInfo(vo);

        return ResultObject.ok();
    }

    /**
     * 管理员将指定影视信息放入回收站中
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @DeleteMapping("delete_into_recycle_bin")
    public ResultObject<Object> logicDeleteVideoInfo(@RequestParam String videoInfoId) throws IOException {
        videoInfoService.addVideoInfoToRecycleBin(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 管理员将指定影视信息直接删除
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @DeleteMapping("delete_video_info")
    public ResultObject<Object> deleteVideoInfo(@RequestParam String videoInfoId) throws IOException {
        videoInfoService.deleteVideoInfoDirectly(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 根据根分类id获取少量热门影视信息
     */
    @GetMapping("top_video_info_by_root_category_id")
    public ResultObject<List<VideoInfoES>> topVideoInfoByRootCategoryId(@RequestParam Integer categoryId){
        return ResultObject.ok(videoInfoService.getVideoInfoByRootCategoryId(categoryId));
    }

    /**
     * 根据根分类id获取大量热门影视信息
     */
    @GetMapping("top_plenty_video_info_by_root_category_id")
    public ResultObject<List<VideoInfoES>> topPlentyVideoInfoByRootCategoryId(@RequestParam Integer categoryId){
        return ResultObject.ok(videoInfoService.getPlentyVideoInfoByRootCategoryId(categoryId));
    }

    /**
     * 获取最近上映影视信息
     */
    @GetMapping("recent_up")
    public ResultObject<List<VideoInfoES>> recentUp(){
        return ResultObject.ok(videoInfoService.getRecentUpVideoInfo());
    }
}
