package neko.movie.nekomoviemember.service.impl;

import cn.hutool.json.JSONUtil;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.RoleSortType;
import neko.movie.nekomoviemember.entity.UserRole;
import neko.movie.nekomoviemember.entity.WeightRoleRelation;
import neko.movie.nekomoviemember.mapper.WeightRoleRelationMapper;
import neko.movie.nekomoviemember.service.UserRoleRelationService;
import neko.movie.nekomoviemember.service.UserRoleService;
import neko.movie.nekomoviemember.service.UserWeightService;
import neko.movie.nekomoviemember.service.WeightRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviemember.vo.NewWeightRoleRelationVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限，角色关系表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class WeightRoleRelationServiceImpl extends ServiceImpl<WeightRoleRelationMapper, WeightRoleRelation> implements WeightRoleRelationService {
    @Resource
    private UserRoleRelationService userRoleRelationService;

    @Resource
    private UserWeightService userWeightService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 用户权限信息获取
     */
    @Override
    public List<WeightRoleRelation> getRelations(String uid) {
        String key = Constant.MEMBER_REDIS_PREFIX + "weight_cache:" + uid;
        String relationCache = stringRedisTemplate.opsForValue().get(key);

        //缓存有数据
        if(relationCache != null){
            return JSONUtil.toList(JSONUtil.parseArray(relationCache), WeightRoleRelation.class);
        }

        List<WeightRoleRelation> relations = this.baseMapper.getRelationsByRoleIds(userRoleRelationService.getUserRoleIds(uid));
        //缓存无数据，查询存入缓存
        stringRedisTemplate.opsForValue().setIfAbsent(key,
                JSONUtil.toJsonStr(relations),
                1000 * 60 * 60 * 5,
                TimeUnit.MILLISECONDS);

        return relations;
    }

    /**
     * 获取指定uid权限
     */
    @Override
    public List<String> getWeightTypesByUid(String uid) {
        return getRelations(uid).stream().filter(Objects::nonNull)
                .map(WeightRoleRelation::getWeightType)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取指定uid角色
     */
    @Override
    public List<String> getRoleTypesByUid(String uid) {
        return getRelations(uid).stream().filter(Objects::nonNull)
                .map(WeightRoleRelation::getRoleType)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取指定roleId权限，角色关系
     */
    @Override
    public List<WeightRoleRelation> getRelationsByRoleId(Integer roleId) {
        return this.baseMapper.getRelationSbyRoleId(roleId);
    }

    /**
     * 新增普通类型权限，角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newRelations(NewWeightRoleRelationVo vo) {
        LocalDateTime now = LocalDateTime.now();

        List<WeightRoleRelation> relations = vo.getWeightIds().stream().filter(Objects::nonNull)
                .distinct()
                .map(w -> new WeightRoleRelation().setRoleId(vo.getRoleId())
                        .setWeightId(w)
                        .setCreateTime(now)
                        .setUpdateTime(now)).collect(Collectors.toList());

        this.saveBatch(relations);
    }

    /**
     * 新增会员等级类型权限，角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newMemberLevelRelations(NewWeightRoleRelationVo vo) {
        UserRole userRole = userRoleService.getById(vo.getRoleId());
        if(!userRole.getType().equals(RoleSortType.MEMBER_LEVEL_TYPE)){
            throw new IllegalArgumentException("角色类型错误");
        }

        if(!userWeightService.checkWeightIdsAreMemberLevelType(vo.getWeightIds())){
            throw new IllegalArgumentException("权限类型错误");
        }

        newRelations(vo);
    }
}
