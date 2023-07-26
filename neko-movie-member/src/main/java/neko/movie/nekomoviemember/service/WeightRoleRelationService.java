package neko.movie.nekomoviemember.service;

import neko.movie.nekomoviemember.entity.WeightRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.vo.NewWeightRoleRelationVo;

import java.util.List;

/**
 * <p>
 * 权限，角色关系表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface WeightRoleRelationService extends IService<WeightRoleRelation> {
    List<WeightRoleRelation> getRelations(String uid);

    List<String> getWeightTypesByUid(String uid);

    List<String> getRoleTypesByUid(String uid);

    List<WeightRoleRelation> getRelationsByRoleId(Integer roleId);

    void newRelations(NewWeightRoleRelationVo vo);

    void newMemberLevelRelations(NewWeightRoleRelationVo vo);
}
