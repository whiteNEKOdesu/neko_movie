package neko.movie.nekomoviemember.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.RoleSortType;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.entity.UserRole;
import neko.movie.nekomoviemember.entity.WeightRoleRelation;
import neko.movie.nekomoviemember.mapper.MemberLevelDictMapper;
import neko.movie.nekomoviemember.mapper.UserRoleMapper;
import neko.movie.nekomoviemember.mapper.WeightRoleRelationMapper;
import neko.movie.nekomoviemember.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviemember.vo.NewUserRoleVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private WeightRoleRelationMapper weightRoleRelationMapper;

    @Resource
    private MemberLevelDictMapper memberLevelDictMapper;

    /**
     * 新增非会员等级类型角色信息角色
     */
    @Override
    public void newUserRole(NewUserRoleVo vo) {
        if(this.baseMapper.selectOne(new QueryWrapper<UserRole>().eq("role_type", vo.getRoleType())) != null){
            throw new DuplicateKeyException("roleType重复");
        }

        UserRole userRole = new UserRole();
        LocalDateTime now = LocalDateTime.now();
        BeanUtil.copyProperties(vo, userRole);
        userRole.setCreateTime(now)
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

    /**
     * 删除普通角色，管理员角色类型角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(Integer roleId) {
        UserRole userRole = this.baseMapper.selectById(roleId);
        if(userRole == null || !userRole.getType().equals(RoleSortType.NORMAL_TYPE) ||
                !userRole.getType().equals(RoleSortType.ADMIN_TYPE)){
            return;
        }

        List<WeightRoleRelation> weightRoleRelations = weightRoleRelationMapper.getRelationSbyRoleId(roleId);
        List<Integer> relationIds = weightRoleRelations.stream().map(WeightRoleRelation::getRelationId)
                .collect(Collectors.toList());

        if(!relationIds.isEmpty()){
            //删除权限，角色关系信息
            weightRoleRelationMapper.deleteBatchIds(relationIds);
        }

        //删除角色信息
        this.baseMapper.delete(new UpdateWrapper<UserRole>().lambda()
                .eq(UserRole::getRoleId, roleId)
                .and(wrapper -> wrapper.eq(UserRole::getType, RoleSortType.NORMAL_TYPE)
                        .or()
                        .eq(UserRole::getType, RoleSortType.ADMIN_TYPE)));
    }

    /**
     * 删除会员等级角色类型角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberLevelRole(Integer roleId) {
        UserRole userRole = this.baseMapper.selectById(roleId);
        if(userRole == null || !userRole.getType().equals(RoleSortType.MEMBER_LEVEL_TYPE)){
            return;
        }

        List<WeightRoleRelation> weightRoleRelations = weightRoleRelationMapper.getRelationSbyRoleId(roleId);
        List<Integer> relationIds = weightRoleRelations.stream().map(WeightRoleRelation::getRelationId)
                .collect(Collectors.toList());

        if(!relationIds.isEmpty()){
            //删除权限，角色关系信息
            weightRoleRelationMapper.deleteBatchIds(relationIds);
        }

        //删除角色对应用户等级字典表信息
        memberLevelDictMapper.delete(new QueryWrapper<MemberLevelDict>().lambda()
                .eq(MemberLevelDict::getRoleId, roleId));

        //删除角色信息
        this.baseMapper.delete(new UpdateWrapper<UserRole>().lambda()
                .eq(UserRole::getRoleId, roleId)
                .eq(UserRole::getType, RoleSortType.MEMBER_LEVEL_TYPE));
    }
}
