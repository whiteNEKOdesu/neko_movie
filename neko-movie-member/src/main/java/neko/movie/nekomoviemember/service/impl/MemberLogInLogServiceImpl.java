package neko.movie.nekomoviemember.service.impl;

import neko.movie.nekomoviemember.entity.MemberLogInLog;
import neko.movie.nekomoviemember.mapper.MemberLogInLogMapper;
import neko.movie.nekomoviemember.service.MemberLogInLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class MemberLogInLogServiceImpl extends ServiceImpl<MemberLogInLogMapper, MemberLogInLog> implements MemberLogInLogService {

    /**
     * 新增用户登录记录
     */
    @Override
    public int newLog(String uid, String ip, Boolean isLogIn) {
        MemberLogInLog memberLogInLog = new MemberLogInLog();
        memberLogInLog.setUid(uid)
                .setIp(ip)
                .setIsLogIn(isLogIn)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        return this.baseMapper.insert(memberLogInLog);
    }
}
