package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.WeightSortType;
import neko.movie.nekomoviemember.entity.UserWeight;
import neko.movie.nekomoviemember.mapper.UserWeightMapper;
import neko.movie.nekomoviemember.service.UserWeightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class UserWeightServiceImpl extends ServiceImpl<UserWeightMapper, UserWeight> implements UserWeightService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增普通权限
     */
    @Override
    public void newUserWeight(String weightType) {
        if(this.baseMapper.getUserWeightByWeightType(weightType) != null){
            throw new DuplicateKeyException("weightType重复");
        }

        UserWeight userWeight = new UserWeight();
        LocalDateTime now = LocalDateTime.now();
        userWeight.setWeightType(weightType)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(userWeight);
    }

    /**
     * 分页查询普通权限信息
     */
    @Override
    public Page<UserWeight> getUserWeightByQueryLimitedPage(QueryVo vo) {
        Page<UserWeight> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<UserWeight> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserWeight::getType, WeightSortType.NORMAL_TYPE);
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(UserWeight::getWeightType, vo.getQueryWords());
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 获取指定roleId还未绑定普通权限信息
     */
    @Override
    public List<UserWeight> getUnbindWeightByRoleId(Integer roleId) {
        return this.baseMapper.getUnbindUserWeightByRoleId(roleId);
    }

    /**
     * 新增会员等级类型权限
     */
    @Override
    public void newMemberLevelWeight(String weightType) {
        if(this.baseMapper.getUserWeightByWeightType(weightType) != null){
            throw new DuplicateKeyException("weightType重复");
        }

        UserWeight userWeight = new UserWeight();
        LocalDateTime now = LocalDateTime.now();
        userWeight.setWeightType(weightType)
                .setType(WeightSortType.MEMBER_LEVEL_TYPE)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(userWeight);

        String key = Constant.MEMBER_REDIS_PREFIX + "member_level_weight_info";
        //删除会员等级类型权限缓存
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取指定roleId还未绑定会员等级权限信息
     */
    @Override
    public List<UserWeight> getUnbindMemberLevelWeightByRoleId(Integer roleId) {
        return this.baseMapper.getUnbindMemberLevelWeightByRoleId(roleId);
    }

    /**
     * 检查weightId list是否为会员等级类型
     */
    @Override
    public boolean checkWeightIdsAreMemberLevelType(List<Integer> weightIds) {
        return weightIds.size() == this.baseMapper.getMemberLevelWeightNumberByWeightIds(weightIds);
    }

    /**
     * 分页查询会员等级类型权限信息
     */
    @Override
    public Page<UserWeight> getMemberLevelUserWeightByQueryLimitedPage(QueryVo vo) {
        Page<UserWeight> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<UserWeight> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserWeight::getType, WeightSortType.MEMBER_LEVEL_TYPE);
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(UserWeight::getWeightType, vo.getQueryWords());
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 获取会员等级类型全部权限信息
     */
    @Override
    public List<UserWeight> getMemberLevelUserWeights() {
        return this.baseMapper.selectList(new QueryWrapper<UserWeight>().lambda()
                .eq(UserWeight::getType, WeightSortType.MEMBER_LEVEL_TYPE));
    }

    /**
     * 根据weightId获取会员等级类型权限名
     */
    @Override
    public String getMemberLevelWeightTypeByWeightId(Integer weightId) {
        return this.baseMapper.getMemberLevelWeightTypeByWeightId(weightId);
    }

    /**
     * 删除指定weightId普通权限类型权限名
     */
    @Override
    public void deleteUserWeight(Integer weightId) {
        this.baseMapper.delete(new UpdateWrapper<UserWeight>().lambda()
                .eq(UserWeight::getWeightId, weightId)
                //匹配普通权限类型
                .eq(UserWeight::getType, WeightSortType.NORMAL_TYPE));
    }

    /**
     * 删除指定weightId会员等级类型权限名
     */
    @Override
    public void deleteMemberLevelWeight(Integer weightId) {
        this.baseMapper.delete(new UpdateWrapper<UserWeight>().lambda()
                .eq(UserWeight::getWeightId, weightId)
                //匹配会员等级权限类型
                .eq(UserWeight::getType, WeightSortType.MEMBER_LEVEL_TYPE));
    }
}
