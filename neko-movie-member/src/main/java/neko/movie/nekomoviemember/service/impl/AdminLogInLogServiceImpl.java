package neko.movie.nekomoviemember.service.impl;

import neko.movie.nekomoviemember.entity.AdminLogInLog;
import neko.movie.nekomoviemember.mapper.AdminLogInLogMapper;
import neko.movie.nekomoviemember.service.AdminLogInLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 管理员登录记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class AdminLogInLogServiceImpl extends ServiceImpl<AdminLogInLogMapper, AdminLogInLog> implements AdminLogInLogService {

    @Override
    public int newLog(String uid, String ip, Boolean isLogIn) {
        AdminLogInLog adminLogInLog = new AdminLogInLog();
        adminLogInLog.setAdminId(uid)
                .setIp(ip)
                .setIsLogIn(isLogIn)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        return this.baseMapper.insert(adminLogInLog);
    }
}
