package neko.movie.nekomoviemember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import neko.movie.nekomoviemember.entity.UserRoleRelation;
import neko.movie.nekomoviemember.to.MemberLevelExpireTo;

/**
 * <p>
 * 用户，会员等级关系表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
public interface MemberLevelRelationService extends IService<MemberLevelRelation> {
    MemberLevelRelation newMemberLevelRelation(String uid, Integer memberLevelId, Integer payLevelMonths);

    void expireMemberLevel(MemberLevelExpireTo to);

    UserRoleRelation getUserRoleRelationByRelationId(String relationId);
}
