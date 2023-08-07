package neko.movie.nekomoviemember.service;

import neko.movie.nekomoviemember.entity.UserRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户，角色关系表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface UserRoleRelationService extends IService<UserRoleRelation> {
    List<Integer> getUserRoleIds(String uid);

    int newRelation(String uid, Integer roleId);

    void newRelations(String uid, List<Integer> roleIds);

    void deleteUserRoleRelationByRelationId(String relationId, Integer updateVersion);
}
