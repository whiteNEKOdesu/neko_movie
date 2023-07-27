package neko.movie.nekomoviemember.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomoviemember.entity.UserRole;
import neko.movie.nekomoviemember.service.UserRoleService;
import neko.movie.nekomoviemember.vo.NewUserRoleVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-04-01
 */
@RestController
@RequestMapping("user_role")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    /**
     * 新增非会员等级类型角色信息角色
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PutMapping("new_user_role")
    public ResultObject<Object> newUserRole(@Validated @RequestBody NewUserRoleVo vo){
        userRoleService.newUserRole(vo);

        return ResultObject.ok();
    }

    /**
     * 分页查询非会员等级类型角色信息角色信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("role_info")
    public ResultObject<Page<UserRole>> roleInfo(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(userRoleService.getUserRolesByQueryLimitedPage(vo));
    }

    /**
     * 查询管理员角色信息
     */
    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PostMapping("admin_role_info")
    public ResultObject<List<UserRole>> adminRoleInfo(){
        return ResultObject.ok(userRoleService.getAdminRoles());
    }

    /**
     * 新增会员等级类型角色信息角色
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_member_level_role")
    public ResultObject<Object> newMemberLevelRole(@RequestParam String roleType){
        userRoleService.newMemberLevelRole(roleType);

        return ResultObject.ok();
    }
}
