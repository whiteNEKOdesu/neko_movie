package neko.movie.nekomoviemember.mapper;

import neko.movie.nekomoviemember.entity.UserRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户，角色关系表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {
    List<Integer> getRoleIdsByUid(String uid);
}
