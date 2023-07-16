package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviemember.entity.UserWeight;
import neko.movie.nekomoviemember.mapper.UserWeightMapper;
import neko.movie.nekomoviemember.service.UserWeightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 新增权限
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
     * 分页查询权限信息
     */
    @Override
    public Page<UserWeight> getUserWeightByQueryLimitedPage(QueryVo vo) {
        Page<UserWeight> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<UserWeight> queryWrapper = new QueryWrapper<>();
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(UserWeight::getWeightType, vo.getQueryWords());
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 获取指定roleId还未绑定权限信息
     */
    @Override
    public List<UserWeight> getUnbindWeightByRoleId(Integer roleId) {
        return this.baseMapper.getUnbindUserWeightByRoleId(roleId);
    }
}
