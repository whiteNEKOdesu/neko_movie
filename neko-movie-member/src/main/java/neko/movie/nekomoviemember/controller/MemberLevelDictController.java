package neko.movie.nekomoviemember.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import neko.movie.nekomoviemember.vo.MemberLevelDictVo;
import neko.movie.nekomoviemember.vo.NewMemberLevelDictVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户等级字典表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("member_level_dict")
public class MemberLevelDictController {
    @Resource
    private MemberLevelDictService memberLevelDictService;

    /**
     * 获取用户等级信息
     */
    @GetMapping("level_infos")
    public ResultObject<List<MemberLevelDictVo>> levelInfos(){
        return ResultObject.ok(memberLevelDictService.getMemberLevelDictInfo());
    }

    /**
     * 管理员添加用户等级信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_level")
    public ResultObject<Object> newLevel(@Validated @RequestBody NewMemberLevelDictVo vo){
        memberLevelDictService.newMemberLevelDict(vo);

        return ResultObject.ok();
    }
}
