package neko.movie.nekomoviemember.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import neko.movie.nekomoviecommonbase.utils.entity.BaseRoleId;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.entity.UserRole;
import neko.movie.nekomoviemember.mapper.MemberLevelDictMapper;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviemember.service.UserRoleService;
import neko.movie.nekomoviemember.vo.MemberLevelDictVo;
import neko.movie.nekomoviemember.vo.NewMemberLevelDictVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户等级字典表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class MemberLevelDictServiceImpl extends ServiceImpl<MemberLevelDictMapper, MemberLevelDict> implements MemberLevelDictService {
    @Resource
    private UserRoleService userRoleService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取用户等级信息
     */
    @Override
    public List<MemberLevelDictVo> getMemberLevelDictInfo() {
        return this.baseMapper.getMemberLevelDictInfo();
    }

    /**
     * 添加用户等级信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newMemberLevelDict(NewMemberLevelDictVo vo) {
        //添加会员等级类型角色
        userRoleService.newMemberLevelRole(vo.getRoleType());

        UserRole userRole = userRoleService.getBaseMapper()
                .selectOne(new QueryWrapper<UserRole>().lambda()
                        .eq(UserRole::getRoleType, vo.getRoleType()));

        MemberLevelDict memberLevelDict = new MemberLevelDict();
        LocalDateTime now = LocalDateTime.now();
        memberLevelDict.setRoleId(userRole.getRoleId())
                .setPrice(vo.getPrice().setScale(2, BigDecimal.ROUND_DOWN))
                .setLevel(vo.getLevel())
                .setCreateTime(now)
                .setUpdateTime(now);

        //添加用户等级信息
        this.baseMapper.insert(memberLevelDict);

        String key = Constant.MEMBER_REDIS_PREFIX + "level_info";
        //删除会员等级缓存
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据memberLevelId获取角色名
     */
    @Override
    public String getRoleTypeByMemberLevelId(Integer memberLevelId) {
        return this.baseMapper.getRoleTypeByMemberLevelId(memberLevelId);
    }

    /**
     * 根据memberLevelId获取用户等级信息
     */
    @Override
    public MemberLevelDictVo getMemberLevelDictMemberLevelId(Integer memberLevelId) {
        return this.baseMapper.getMemberLevelDictMemberLevelId(memberLevelId);
    }

    /**
     * 根据uid获取用户最高会员等级角色名
     */
    @Override
    public String getHighestMemberRoleTypeByUid(String uid) {
        String roleType = this.baseMapper.getHighestMemberRoleTypeByUid(uid);

        //roleType为空，则返回普通会员等级角色
        return roleType != null ? roleType : userRoleService.getById(BaseRoleId.BASE_MEMBER_LEVEL_TYPE_ROLE_ID).getRoleType();
    }
}
