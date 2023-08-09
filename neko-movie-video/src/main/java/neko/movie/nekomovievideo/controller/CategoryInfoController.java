package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.entity.CategoryInfo;
import neko.movie.nekomovievideo.service.CategoryInfoService;
import neko.movie.nekomovievideo.vo.NewCategoryInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 影视分类表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-04-01
 */
@RestController
@RequestMapping("category_Info")
public class CategoryInfoController {
    @Resource
    private CategoryInfoService categoryInfoService;

    /**
     * 获取层级影视分类信息
     */
    @GetMapping("level_category_info")
    public ResultObject<List<CategoryInfo>> levelCategoryInfo(){
        return ResultObject.ok(categoryInfoService.getLevelCategory());
    }

    /**
     * 管理员新增影视分类信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_category_info")
    public ResultObject<Object> newCategoryInfo(@Validated @RequestBody NewCategoryInfoVo vo){
        categoryInfoService.newCategoryInfo(vo);

        return ResultObject.ok();
    }

    /**
     * 管理员删除叶节点影视分类信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @DeleteMapping("delete_leaf_category_info")
    public ResultObject<Object> deleteLeafCategoryInfo(@RequestParam Integer categoryId) throws InterruptedException {
        categoryInfoService.deleteLeafCategoryInfo(categoryId);

        return ResultObject.ok();
    }
}
