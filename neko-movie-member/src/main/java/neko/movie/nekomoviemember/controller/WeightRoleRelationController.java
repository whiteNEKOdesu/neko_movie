package neko.movie.nekomoviemember.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomoviemember.entity.WeightRoleRelation;
import neko.movie.nekomoviemember.service.WeightRoleRelationService;
import neko.movie.nekomoviemember.vo.NewWeightRoleRelationVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限，角色关系表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-04-01
 */
@RestController
@RequestMapping("weight_role_relation")
public class WeightRoleRelationController {
    @Resource
    private WeightRoleRelationService weightRoleRelationService;

    @SaCheckRole(RoleType.ROOT)
    @SaCheckLogin
    @PutMapping("new_relations")
    public ResultObject<Object> newRelations(@Validated @RequestBody NewWeightRoleRelationVo vo){
        weightRoleRelationService.newRelations(vo);

        return ResultObject.ok();
    }

    /**
     * 获指定roleId权限，角色关系
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PostMapping("relation_info_by_role_id")
    public ResultObject<List<WeightRoleRelation>> relationInfoByRoleId(@RequestParam Integer roleId){
        return ResultObject.ok(weightRoleRelationService.getRelationsByRoleId(roleId));
    }

    /**
     * 获指定uid权限，角色关系
     * 建议只提供给微服务远程调用
     */
    @PostMapping("relation_info_by_uid")
    public ResultObject<List<WeightRoleRelation>> relationInfoByUid(@RequestParam String uid){
        return ResultObject.ok(weightRoleRelationService.getRelations(uid));
    }
}
