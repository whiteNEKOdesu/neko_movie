package neko.movie.nekomoviemember.mapper;

import neko.movie.nekomoviemember.entity.MemberLevelDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomoviemember.vo.MemberLevelDictVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户等级字典表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface MemberLevelDictMapper extends BaseMapper<MemberLevelDict> {
    List<MemberLevelDictVo> getMemberLevelDictInfo();

    String getRoleTypeByMemberLevelId(Integer memberLevelId);

    MemberLevelDictVo getMemberLevelDictMemberLevelId(Integer memberLevelId);

    String getHighestMemberRoleTypeByUid(String uid);
}
