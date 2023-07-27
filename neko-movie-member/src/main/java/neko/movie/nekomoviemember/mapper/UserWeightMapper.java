package neko.movie.nekomoviemember.mapper;

import neko.movie.nekomoviemember.entity.UserWeight;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface UserWeightMapper extends BaseMapper<UserWeight> {
    UserWeight getUserWeightByWeightType(String weightType);

    List<UserWeight> getUnbindUserWeightByRoleId(Integer roleId);

    List<UserWeight> getUnbindMemberLevelWeightByRoleId(Integer roleId);

    int getMemberLevelWeightNumberByWeightIds(List<Integer> weightIds);

    String getMemberLevelWeightTypeByWeightId(Integer weightId);
}
