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

    /**
     * 管理员分页查询会员等级类型权限信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("member_level_weight_info")
    public ResultObject<Page<UserWeight>> memberLevelWeightInfo(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(userWeightService.getMemberLevelUserWeightByQueryLimitedPage(vo));
    }

    /**
     * 获取会员等级类型全部权限信息
     */
    @GetMapping("member_level_weight_infos")
    public ResultObject<List<UserWeight>> memberLevelWeightInfos(){
        return ResultObject.ok(userWeightService.getMemberLevelUserWeights());
    }

    /**
     * 根据weightId获取会员等级类型权限名，建议只提供给微服务远程调用
     */
    @GetMapping("member_level_weight_name_by_weight_id")
    public ResultObject<String> memberLevelWeightNameByWeightId(@RequestParam Integer weightId){
        return ResultObject.ok(userWeightService.getMemberLevelWeightTypeByWeightId(weightId));
    }

    /**
     * 管理员删除指定weightId普通权限类型权限名
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @DeleteMapping("delete_weight")
    public ResultObject<Object> deleteWeight(@RequestParam Integer weightId){
        userWeightService.deleteUserWeight(weightId);

        return ResultObject.ok();
    }

    /**
     * 管理员删除指定weightId会员等级类型权限名
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @DeleteMapping("delete_member_level_weight")
    public ResultObject<Object> deleteMemberLevelWeight(@RequestParam Integer weightId){
        userWeightService.deleteMemberLevelWeight(weightId);

        return ResultObject.ok();
    }
}
