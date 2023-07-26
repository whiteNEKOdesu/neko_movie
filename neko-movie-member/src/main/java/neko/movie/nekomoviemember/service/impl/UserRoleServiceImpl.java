package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.RoleSortType;
import neko.movie.nekomoviemember.entity.UserRole;
import neko.movie.nekomoviemember.mapper.UserRoleMapper;
import neko.movie.nekomoviemember.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    /**
     * 新增非会员等级类型角色信息角色
     */
    @Override
    public void newUserRole(String roleType) {
        if(this.baseMapper.selectOne(new QueryWrapper<UserRole>().eq("role_type", roleType)) != null){
            throw new DuplicateKeyException("roleType重复");
        }

        UserRole userRole = new UserRole();
        LocalDateTime now = LocalDateTime.now();
        userRole.setRoleType(roleType)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(userRole);
    }

    /**
     * 分页查询非会员等级类型角色信息
     */
    @Override
    public Page<UserRole> getUserRolesByQueryLimitedPage(QueryVo vo) {
        Page<UserRole> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(UserRole::getType, RoleSortType.MEMBER_LEVEL_TYPE);
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(UserRole::getRoleType, vo.getQueryWords());
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 查询管理员角色信息
     */
    @Override
    public List<UserRole> getAdminRoles() {
        return this.lambdaQuery().eq(UserRole::getType, RoleSortType.ADMIN_TYPE).list();
    }

    /**
     * 根据角色名获取角色信息
     */
    @Override
    public UserRole getUserRoleByRoleType(String roleType) {
        return this.baseMapper.selectOne(new QueryWrapper<UserRole>()
                .lambda()
                .eq(UserRole::getRoleType, roleType));
    }

    /**
     * 新增会员等级类型角色信息角色
     */
    @Override
    public void newMemberLevelRole(String roleType) {
        if(this.baseMapper.selectOne(new QueryWrapper<UserRole>().eq("role_type", roleType)) != null){
            throw new DuplicateKeyException("roleType重复");
        }

        UserRole userRole = new UserRole();
        LocalDateTime now = LocalDateTime.now();
        userRole.setRoleType(roleType)
                .setType(RoleSortType.MEMBER_LEVEL_TYPE)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(userRole);
    }
}
