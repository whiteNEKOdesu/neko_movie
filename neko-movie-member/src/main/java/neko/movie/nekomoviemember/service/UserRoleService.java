package neko.movie.nekomoviemember.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviemember.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.vo.NewUserRoleVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface UserRoleService extends IService<UserRole> {
    void newUserRole(NewUserRoleVo vo);

    Page<UserRole> getUserRolesByQueryLimitedPage(QueryVo vo);

    List<UserRole> getAdminRoles();

    UserRole getUserRoleByRoleType(String roleType);

    void newMemberLevelRole(String roleType);

    void deleteUserRole(Integer roleId);

    void deleteMemberLevelRole(Integer roleId);
}
