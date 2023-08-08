package neko.movie.nekomoviemember.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import neko.movie.nekomoviemember.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户，会员等级关系表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Mapper
public interface MemberLevelRelationMapper extends BaseMapper<MemberLevelRelation> {
    int updateExistMemberLevelRelation(String relationId,
                                        LocalDateTime levelExpireTime,
                                        Integer updateVersion,
                                        LocalDateTime updateTime);

    UserRoleRelation getUserRoleRelationByRelationId(String relationId);

    /**
     * 根据uid获取用户会员类型角色名
     */
    List<String> getMemberLevelRoleTypesByUid(String uid);
}
