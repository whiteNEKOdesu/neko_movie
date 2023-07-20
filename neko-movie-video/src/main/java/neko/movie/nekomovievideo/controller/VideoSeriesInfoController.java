package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 影视集数信息表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("video_series_info")
public class VideoSeriesInfoController {
    @Resource
    private VideoSeriesInfoService videoSeriesInfoService;

    /**
     * 管理员分页查询指定videoSeriesId视频分集信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("admin_video_series_infos")
    public ResultObject<Page<VideoSeriesInfo>> adminVideoSeriesInfos(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(videoSeriesInfoService.getVideoSeriesInfoForAdminByQueryLimitedPage(vo));
    }

    /**
     * 管理员添加视频分集集数
     */
    @PutMapping("new_video_series")
    public ResultObject<Object> newVideoSeries(@RequestParam String videoInfoId,
                                               @RequestParam Integer seriesNumber,
                                               @RequestParam Integer requireMemberLevelId,
                                               @RequestPart MultipartFile file) throws InterruptedException {
        videoSeriesInfoService.newVideoSeriesInfo(videoInfoId,
                seriesNumber,
                requireMemberLevelId,
                file);

        return ResultObject.ok();
    }
}
