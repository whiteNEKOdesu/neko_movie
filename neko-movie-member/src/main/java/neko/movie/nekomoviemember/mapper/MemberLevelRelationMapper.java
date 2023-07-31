package neko.movie.nekomoviemember.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomoviemember.entity.MemberLevelRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户，会员等级关系表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Mapper
public interface MemberLevelRelationMapper extends BaseMapper<MemberLevelRelation> {

}
