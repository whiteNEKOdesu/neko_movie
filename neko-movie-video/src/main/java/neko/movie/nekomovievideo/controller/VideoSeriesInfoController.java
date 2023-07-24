package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import neko.movie.nekomovievideo.vo.VideoSeriesInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

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
     * 分页查询指定videoSeriesId视频分集信息
     */
    @SaCheckLogin
    @PostMapping("admin_video_series_infos")
    public ResultObject<Page<VideoSeriesInfoVo>> adminVideoSeriesInfos(@Validated @RequestBody QueryVo vo) throws ExecutionException, InterruptedException {
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

    /**
     * 根据影视集数id获取影视单集信息
     */
    @PostMapping("video_series_info_by_id")
    public ResultObject<VideoSeriesInfoVo> videoSeriesInfoById(@RequestParam String videoSeriesId){
        return ResultObject.ok(videoSeriesInfoService.getVideoSeriesInfoByVideoSeriesInfoId(videoSeriesId));
    }
}
