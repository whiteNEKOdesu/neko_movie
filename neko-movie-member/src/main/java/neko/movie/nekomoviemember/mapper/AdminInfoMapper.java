package neko.movie.nekomoviemember.mapper;

import neko.movie.nekomoviemember.entity.AdminInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    AdminInfo getAdminInfoByUserName(String userName);
}
