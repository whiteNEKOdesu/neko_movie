package neko.movie.nekomoviemember.service;

import neko.movie.nekomoviemember.entity.MemberLevelDict;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.vo.MemberLevelDictVo;
import neko.movie.nekomoviemember.vo.NewMemberLevelDictVo;

import java.util.List;

/**
 * <p>
 * 用户等级字典表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface MemberLevelDictService extends IService<MemberLevelDict> {
    List<MemberLevelDictVo> getMemberLevelDictInfo();

    void newMemberLevelDict(NewMemberLevelDictVo vo);

    String getRoleTypeByMemberLevelId(Integer memberLevelId);

    MemberLevelDictVo getMemberLevelDictMemberLevelId(Integer memberLevelId);
}
