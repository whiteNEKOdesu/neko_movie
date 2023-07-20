package neko.movie.nekomoviemember.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultObject<List<MemberLevelDict>> levelInfos(){
        return ResultObject.ok(memberLevelDictService.getMemberLevelDictInfo());
    }
}
