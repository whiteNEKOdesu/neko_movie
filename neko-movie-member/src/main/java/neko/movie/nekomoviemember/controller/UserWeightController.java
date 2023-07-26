package neko.movie.nekomoviemember.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomoviemember.entity.UserWeight;
import neko.movie.nekomoviemember.service.UserWeightService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-04-01
 */
@RestController
@RequestMapping("user_weight")
public class UserWeightController {
    @Resource
    private UserWeightService userWeightService;

    /**
     * 管理员新增普通权限
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PutMapping("new_user_weight")
    public ResultObject<Object> newUserWeight(@RequestParam String weightType){
        userWeightService.newUserWeight(weightType);

        return ResultObject.ok();
    }

    /**
     * 管理员分页查询普通权限信息
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PostMapping("weight_info")
    public ResultObject<Page<UserWeight>> weightInfo(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(userWeightService.getUserWeightByQueryLimitedPage(vo));
    }

    /**
     * 管理员获取指定roleId还未绑定普通权限信息
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PostMapping("unbind_weight_info")
    public ResultObject<List<UserWeight>> unbindWeightInfo(@RequestParam Integer roleId){
        return ResultObject.ok(userWeightService.getUnbindWeightByRoleId(roleId));
    }

    /**
     * 管理员新增会员等级类型权限
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_member_level_weight")
    public ResultObject<Object> newMemberLevelWeight(@RequestParam String weightType){
        userWeightService.newMemberLevelWeight(weightType);

        return ResultObject.ok();
    }

    /**
     * 管理员获取指定roleId还未绑定会员等级权限信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("unbind_member_level_weight_info")
    public ResultObject<List<UserWeight>> unbindMemberLevelWeightInfo(@RequestParam Integer roleId){
        return ResultObject.ok(userWeightService.getUnbindMemberLevelWeightByRoleId(roleId));
    }
}
