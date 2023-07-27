package neko.movie.nekomoviemember.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviemember.entity.UserWeight;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface UserWeightService extends IService<UserWeight> {
    void newUserWeight(String weightType);

    Page<UserWeight> getUserWeightByQueryLimitedPage(QueryVo vo);

    List<UserWeight> getUnbindWeightByRoleId(Integer roleId);

    void newMemberLevelWeight(String weightType);

    List<UserWeight> getUnbindMemberLevelWeightByRoleId(Integer roleId);

    boolean checkWeightIdsAreMemberLevelType(List<Integer> weightIds);

    Page<UserWeight> getMemberLevelUserWeightByQueryLimitedPage(QueryVo vo);

    List<UserWeight> getMemberLevelUserWeights();

    String getMemberLevelWeightTypeByWeightId(Integer weightId);
}
