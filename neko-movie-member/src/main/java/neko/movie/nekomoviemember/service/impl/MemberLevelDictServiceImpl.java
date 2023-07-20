package neko.movie.nekomoviemember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.mapper.MemberLevelDictMapper;
import neko.movie.nekomoviemember.service.MemberLevelDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    /**
     * 获取用户等级信息
     */
    @Override
    public List<MemberLevelDict> getMemberLevelDictInfo() {
        return this.baseMapper.selectList(new QueryWrapper<MemberLevelDict>().lambda()
                .eq(MemberLevelDict::getIsDelete, false)
                .orderByAsc(MemberLevelDict::getMemberLevel));
    }
}
