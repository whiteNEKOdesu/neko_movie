package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.exception.MemberLevelUpdateException;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import neko.movie.nekomoviemember.entity.UserRoleRelation;
import neko.movie.nekomoviemember.mapper.MemberLevelRelationMapper;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import neko.movie.nekomoviemember.service.MemberLevelRelationService;
import neko.movie.nekomoviemember.service.UserRoleRelationService;
import neko.movie.nekomoviemember.to.MemberLevelExpireTo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户，会员等级关系表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Service
@Slf4j
public class MemberLevelRelationServiceImpl extends ServiceImpl<MemberLevelRelationMapper, MemberLevelRelation> implements MemberLevelRelationService {
    @Resource
    private MemberLevelDictService memberLevelDictService;

    @Resource
    private UserRoleRelationService userRoleRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加用户，会员等级关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberLevelRelation newMemberLevelRelation(String uid, Integer memberLevelId, Integer payLevelMonths) {
        MemberLevelDict memberLevelDict = memberLevelDictService.getById(memberLevelId);
        if(memberLevelDict == null || memberLevelDict.getIsDelete()){
            return null;
        }

        MemberLevelRelation memberLevelRelation = this.baseMapper.selectOne(new QueryWrapper<MemberLevelRelation>().lambda()
                .eq(MemberLevelRelation::getUid, uid)
                .eq(MemberLevelRelation::getMemberLevelId, memberLevelId)
                .eq(MemberLevelRelation::getIsDelete, false));

        LocalDateTime now = LocalDateTime.now();
        //判断用户会员时间是否过期
        if(memberLevelRelation == null){
            memberLevelRelation = new MemberLevelRelation();
            memberLevelRelation.setUid(uid)
                    .setMemberLevelId(memberLevelId)
                    .setLevelExpireTime(now.plusMonths(payLevelMonths))
                    .setCreateTime(now)
                    .setUpdateTime(now);

            //添加用户，会员等级关系
            this.baseMapper.insert(memberLevelRelation);

            //添加用户，会员角色关系
            userRoleRelationService.newRelation(uid, memberLevelDict.getRoleId());
            //删除用户权限缓存
            deleteUserWeightCache(uid);

            return memberLevelRelation.setUpdateVersion(0);
        }else{
            int retryCount = 0;
            //乐观锁自旋
            while(memberLevelRelation != null && retryCount < 10){
                //版本号匹配更新
                if(this.baseMapper.updateExistMemberLevelRelation(memberLevelRelation.getRelationId(),
                        memberLevelRelation.getLevelExpireTime().plusMonths(payLevelMonths),
                        memberLevelRelation.getUpdateVersion(),
                        now) == 1){
                    //删除用户权限缓存
                    deleteUserWeightCache(uid);
                    return memberLevelRelation.setUpdateVersion(memberLevelRelation.getUpdateVersion() + 1);
                }

                memberLevelRelation = this.baseMapper.selectOne(new QueryWrapper<MemberLevelRelation>().lambda()
                        .eq(MemberLevelRelation::getUid, uid)
                        .eq(MemberLevelRelation::getMemberLevelId, memberLevelId)
                        .eq(MemberLevelRelation::getIsDelete, false));
                retryCount++;
            }

            throw new MemberLevelUpdateException("会员等级更新异常");
        }
    }

    /**
     * 用户会员过期删除相关会员权益
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expireMemberLevel(MemberLevelExpireTo to) {
        MemberLevelRelation memberLevelRelation = new MemberLevelRelation();
        memberLevelRelation.setUpdateVersion(to.getUpdateVersion() + 1)
                .setIsDelete(true);
        //比较乐观锁版本号删除用户，会员等级关系
        if(this.baseMapper.update(memberLevelRelation, new UpdateWrapper<MemberLevelRelation>().lambda()
                .eq(MemberLevelRelation::getRelationId, to.getRelationId())
                .eq(MemberLevelRelation::getUpdateVersion, to.getUpdateVersion())) == 1){
            UserRoleRelation userRoleRelation = this.baseMapper.getUserRoleRelationByRelationId(to.getRelationId());
            if(userRoleRelation == null){
                return;
            }
            //根据relationId删除用户，角色关系
            userRoleRelationService.deleteUserRoleRelationByRelationId(userRoleRelation.getRelationId(), userRoleRelation.getUpdateVersion());
            //删除用户权限缓存
            deleteUserWeightCache(userRoleRelation.getUid());

            log.info("uid: " + userRoleRelation.getUid() + "，会员等级角色id: " + userRoleRelation.getRoleId() + "，过期");
        }else{
            log.info("relationId: " + to.getRelationId() + "，乐观锁版本号不一致，不删除相关会员权益");
        }
    }

    /**
     * 根据user_role_relation表relationId获取user_role_relation表信息
     */
    @Override
    public UserRoleRelation getUserRoleRelationByRelationId(String relationId) {
        return this.baseMapper.getUserRoleRelationByRelationId(relationId);
    }

    /**
     * 删除用户权限缓存
     */
    private void deleteUserWeightCache(String uid){
        String key = Constant.MEMBER_REDIS_PREFIX + "weight_cache:" + uid;
        stringRedisTemplate.delete(key);
    }
}
