package neko.movie.nekomoviemember.mapper;

import neko.movie.nekomoviemember.entity.MemberInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface MemberInfoMapper extends BaseMapper<MemberInfo> {
    MemberInfo getMemberInfoByUserName(String userName);
}
