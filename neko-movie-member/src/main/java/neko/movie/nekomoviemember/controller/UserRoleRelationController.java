package neko.movie.nekomoviemember.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviemember.service.UserRoleRelationService;
import neko.movie.nekomoviemember.vo.NewUserRoleRelationVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户，角色关系表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-04-01
 */
@RestController
@RequestMapping("user_role_relation")
public class UserRoleRelationController {
    @Resource
    private UserRoleRelationService userRoleRelationService;

    /**
     * 批量新增uid，角色关系，内部微服务调用
     */
    @PutMapping("new_user_role_relation")
    public ResultObject<Object> newUserRoleRelation(@Validated @RequestBody NewUserRoleRelationVo vo){
        userRoleRelationService.newRelations(vo.getUid(), vo.getRoleIds());

        return ResultObject.ok();
    }
}
