package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.VideoCollectionService;
import neko.movie.nekomovievideo.vo.VideoCollectionVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@RestController
@RequestMapping("video_collection")
public class VideoCollectionController {
    @Resource
    private VideoCollectionService videoCollectionService;

    /**
     * 添加影视收藏
     */
    @SaCheckLogin
    @PutMapping("new_collection")
    public ResultObject<Object> newCollection(@RequestParam String videoInfoId){
        videoCollectionService.newVideoCollection(videoInfoId);

        return ResultObject.ok();
    }

    /**
     * 删除指定collectionId影视收藏
     */
    @SaCheckLogin
    @DeleteMapping("delete_collection")
    public ResultObject<Object> deleteCollection(@RequestParam String collectionId){
        videoCollectionService.deleteVideoCollection(collectionId);

        return ResultObject.ok();
    }

    /**
     * 分页查询用户自身收藏信息
     */
    @SaCheckLogin
    @PostMapping("user_self_collection_infos")
    public ResultObject<Page<VideoCollectionVo>> userSelfCollectionInfos(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(videoCollectionService.getUserSelfVideoCollectionByQueryLimitedPage(vo));
    }
}
