package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.entity.RollVideo;
import neko.movie.nekomovievideo.service.RollVideoService;
import neko.movie.nekomovievideo.vo.NewRollVideoVo;
import neko.movie.nekomovievideo.vo.UpdateRollVideoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 影视信息轮播图表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-08-12
 */
@RestController
@RequestMapping("roll_video")
public class RollVideoController {
    @Resource
    private RollVideoService rollVideoService;

    /**
     * 管理员添加轮播图信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_roll_video")
    public ResultObject<Object> newRollVideo(@Validated NewRollVideoVo vo){
        rollVideoService.newRollVideo(vo);

        return ResultObject.ok();
    }

    /**
     * 获取轮播图信息
     */
    @GetMapping("roll_videos")
    public ResultObject<List<RollVideo>> rollVideos(){
        return ResultObject.ok(rollVideoService.getRollVideos());
    }

    /**
     * 删除指定rollId轮播图信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @DeleteMapping("delete_roll_video")
    public ResultObject<Object> deleteRollVideo(@RequestParam Integer rollId){
        rollVideoService.deleteRollVideo(rollId);

        return ResultObject.ok();
    }

    /**
     * 管理员修改指定rollId轮播图信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("update_roll_video")
    public ResultObject<Object> updateRollVideo(@Validated UpdateRollVideoVo vo){
        rollVideoService.updateRollVideo(vo);

        return ResultObject.ok();
    }
}
