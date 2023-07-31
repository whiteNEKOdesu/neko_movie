package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.exception.MemberLevelUpdateException;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import neko.movie.nekomoviemember.mapper.MemberLevelRelationMapper;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import neko.movie.nekomoviemember.service.MemberLevelRelationService;
import neko.movie.nekomoviemember.service.UserRoleRelationService;
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
public class MemberLevelRelationServiceImpl extends ServiceImpl<MemberLevelRelationMapper, MemberLevelRelation> implements MemberLevelRelationService {
    @Resource
    private MemberLevelDictService memberLevelDictService;

    @Resource
    private UserRoleRelationService userRoleRelationService;

    /**
     * 添加用户，会员等级关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newMemberLevelRelation(String uid, Integer memberLevelId, Integer payLevelMonths) {
        MemberLevelDict memberLevelDict = memberLevelDictService.getById(memberLevelId);
        if(memberLevelDict == null || memberLevelDict.getIsDelete()){
            return;
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
        }else{
            int retryCount = 0;
            //乐观锁自旋
            while(memberLevelRelation != null && retryCount < 10){
                //版本号匹配更新
                if(this.baseMapper.updateExistMemberLevelRelation(memberLevelRelation.getRelationId(),
                        memberLevelRelation.getLevelExpireTime().plusMonths(payLevelMonths),
                        memberLevelRelation.getUpdateVersion(),
                        now) == 1){
                    //添加用户，会员角色关系
                    userRoleRelationService.newRelation(uid, memberLevelDict.getRoleId());

                    return;
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
}
