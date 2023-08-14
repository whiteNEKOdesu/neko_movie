package neko.movie.nekomoviemember.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import neko.movie.nekomoviecommonbase.utils.entity.Response;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.UserNameRepeatException;
import neko.movie.nekomoviemember.entity.AdminInfo;
import neko.movie.nekomoviemember.ip.IPHandler;
import neko.movie.nekomoviemember.mapper.AdminInfoMapper;
import neko.movie.nekomoviemember.service.AdminInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviemember.service.AdminLogInLogService;
import neko.movie.nekomoviemember.service.UserRoleRelationService;
import neko.movie.nekomoviemember.service.WeightRoleRelationService;
import neko.movie.nekomoviemember.vo.AdminInfoVo;
import neko.movie.nekomoviemember.vo.LogInVo;
import neko.movie.nekomoviemember.vo.NewAdminInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Resource
    private UserRoleRelationService userRoleRelationService;

    @Resource
    private AdminLogInLogService adminLogInLogService;

    @Resource
    private WeightRoleRelationService weightRoleRelationService;

    @Resource
    private RSA rsa;

    @Override
    public ResultObject<AdminInfoVo> logIn(LogInVo vo, HttpServletRequest request) {
        ResultObject<AdminInfoVo> resultObject = new ResultObject<>();
        AdminInfo adminInfo = this.baseMapper.getAdminInfoByUserName(vo.getUserName());

        if(adminInfo == null){
            return resultObject.setResponseStatus(Response.USER_LOG_IN_ERROR);
        }else{
            String userPassword = StrUtil.str(rsa.decrypt(Base64.decode(vo.getUserPassword()), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            if(DigestUtils.md5DigestAsHex((userPassword + adminInfo.getSalt()).getBytes()).equals(adminInfo.getUserPassword())){
                StpUtil.login(adminInfo.getAdminId());
                AdminInfoVo adminInfoVo = new AdminInfoVo();
                BeanUtil.copyProperties(adminInfo, adminInfoVo);
                adminInfoVo.setToken(StpUtil.getTokenValue())
                        .setWeightTypes(weightRoleRelationService.getWeightTypesByUid(adminInfo.getAdminId()))
                        .setRoleTypes(weightRoleRelationService.getRoleTypesByUid(adminInfo.getAdminId()));
                resultObject.setResult(adminInfoVo)
                        .setResponseStatus(Response.SUCCESS);
                adminLogInLogService.newLog(adminInfo.getAdminId(),
                        IPHandler.getIP(request),
                        true);
            }else{
                resultObject.setResponseStatus(Response.USER_LOG_IN_ERROR);
                adminLogInLogService.newLog(adminInfo.getAdminId(),
                        IPHandler.getIP(request),
                        false);
            }
        }

        return resultObject.compact();
    }

    /**
     * 新增管理员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newAdmin(NewAdminInfoVo vo) {
        if(userNameIsRepeat(vo.getUserName())){
            throw new UserNameRepeatException("用户名重复");
        }

        AdminInfo adminInfo = new AdminInfo();
        //生成盐
        String salt = Arrays.toString(RandomUtil.randomBytes(10));
        BeanUtil.copyProperties(vo, adminInfo);
        adminInfo.setUserPassword(DigestUtils.md5DigestAsHex((vo.getUserPassword() + salt).getBytes()))
                .setSalt(salt)
                .setOperateAdminId(StpUtil.getLoginId().toString())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        this.baseMapper.insert(adminInfo);
        AdminInfo adminInfoByUserName = this.baseMapper.getAdminInfoByUserName(vo.getUserName());

        //设置管理员角色
        userRoleRelationService.newRelations(adminInfoByUserName.getAdminId(), vo.getRoleIds());
    }

    @Override
    public boolean userNameIsRepeat(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<AdminInfo>().eq("user_name", userName)) != null;
    }
}
