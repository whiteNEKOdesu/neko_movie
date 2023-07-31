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
import neko.movie.nekomoviecommonbase.utils.entity.BaseRoleId;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.Response;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.*;
import neko.movie.nekomoviemember.entity.MemberInfo;
import neko.movie.nekomoviemember.entity.MemberLevelDict;
import neko.movie.nekomoviemember.feign.thirdparty.MailFeignService;
import neko.movie.nekomoviemember.feign.thirdparty.OSSFeignService;
import neko.movie.nekomoviemember.ip.IPHandler;
import neko.movie.nekomoviemember.mapper.MemberInfoMapper;
import neko.movie.nekomoviemember.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviemember.vo.LogInVo;
import neko.movie.nekomoviemember.vo.MemberInfoVo;
import neko.movie.nekomoviemember.vo.UpdateUserPasswordVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {
    @Resource
    private UserRoleRelationService userRoleRelationService;

    @Resource
    private MemberLogInLogService memberLogInLogService;

    @Resource
    private WeightRoleRelationService weightRoleRelationService;

    @Resource
    private MemberLevelDictService memberLevelDictService;

    @Resource
    private MailFeignService mailFeignService;

    @Resource
    private OSSFeignService ossFeignService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RSA rsa;

    @Override
    public ResultObject<MemberInfoVo> logIn(LogInVo vo, HttpServletRequest request) {
        ResultObject<MemberInfoVo> resultObject = new ResultObject<>();
        MemberInfo memberInfo = this.baseMapper.getMemberInfoByUserName(vo.getUserName());

        if(memberInfo == null){
            return resultObject.setResponseStatus(Response.USER_LOG_IN_ERROR);
        }else{
            String userPassword = StrUtil.str(rsa.decrypt(Base64.decode(vo.getUserPassword()), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            if(DigestUtils.md5DigestAsHex((userPassword + memberInfo.getSalt()).getBytes()).equals(memberInfo.getUserPassword())){
                StpUtil.login(memberInfo.getUid());
                MemberInfoVo memberInfoVo = new MemberInfoVo();
                BeanUtil.copyProperties(memberInfo, memberInfoVo);
                memberInfoVo.setMemberLevelRoleType(memberLevelDictService.getHighestMemberRoleTypeByUid(memberInfo.getUid()))
                        .setToken(StpUtil.getTokenValue())
                        .setWeightTypes(weightRoleRelationService.getWeightTypesByUid(memberInfo.getUid()))
                        .setRoleTypes(weightRoleRelationService.getRoleTypesByUid(memberInfo.getUid()));
                resultObject.setResult(memberInfoVo)
                        .setResponseStatus(Response.SUCCESS);
                memberLogInLogService.newLog(memberInfo.getUid(),
                        IPHandler.getIP(request),
                        true);
            }else{
                resultObject.setResponseStatus(Response.USER_LOG_IN_ERROR);
                memberLogInLogService.newLog(memberInfo.getUid(),
                        IPHandler.getIP(request),
                        false);
            }
        }
        return resultObject.compact();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String userName, String userPassword, String email, String code) {
        if(userNameIsRepeat(userName)){
            throw new UserNameRepeatException("用户名重复");
        }

        String key = Constant.MEMBER_REDIS_PREFIX + "register_mail_code:" + email;
        String todoCode = stringRedisTemplate.opsForValue().get(key);

        if(!code.equals(todoCode)){
            throw new CodeIllegalException("验证码错误");
        }

        MemberInfo memberInfo = new MemberInfo();
        //生成盐
        String salt = Arrays.toString(RandomUtil.randomBytes(10));
        memberInfo.setUserName(userName)
                //加盐
                .setUserPassword(DigestUtils.md5DigestAsHex((userPassword + salt).getBytes()))
                .setSalt(salt)
                .setMail(email)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());

        this.baseMapper.insert(memberInfo);
        MemberInfo memberInfoByUserName = this.baseMapper.getMemberInfoByUserName(userName);

        //为用户设置普通用户以及普通会员角色
        userRoleRelationService.newRelations(memberInfoByUserName.getUid(),
                Arrays.asList(BaseRoleId.BASE_NORMAL_TYPE_ROLE_ID, BaseRoleId.BASE_MEMBER_LEVEL_TYPE_ROLE_ID));
    }

    @Override
    public boolean userNameIsRepeat(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<MemberInfo>().eq("user_name", userName)) != null;
    }

    @Override
    public void sendRegisterCode(String email) {
        if(this.lambdaQuery().eq(MemberInfo::getMail, email).exists()){
            throw new EMailAlreadyExistException("邮件已经存在");
        }

        String key = Constant.MEMBER_REDIS_PREFIX + "register_mail_code:" + email;
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(key,
                code,
                1000 * 60 * 5,
                TimeUnit.MILLISECONDS);
        ResultObject<Object> r = mailFeignService.sendRegisterMail(email, code);

        if(r.getResponseCode() != 200){
            throw new MailSendException("邮件发送错误");
        }
    }

    @Override
    public void updateUserPassword(UpdateUserPasswordVo vo) {
        String uid = StpUtil.getLoginId().toString();
        MemberInfo memberInfo = this.baseMapper.selectById(uid);
        if(memberInfo == null){
            throw new NoSuchResultException("无此用户");
        }

        String userPassword = StrUtil.str(rsa.decrypt(Base64.decode(vo.getUserPassword()), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
        if(DigestUtils.md5DigestAsHex((userPassword + memberInfo.getSalt()).getBytes()).equals(memberInfo.getUserPassword())){
            String todoPassword = StrUtil.str(rsa.decrypt(Base64.decode(vo.getTodoPassword()), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            MemberInfo todoMemberInfo = new MemberInfo();
            todoPassword = DigestUtils.md5DigestAsHex((todoPassword + memberInfo.getSalt()).getBytes());
            todoMemberInfo.setUid(uid)
                    .setUserPassword(todoPassword)
                    .setUpdateTime(LocalDateTime.now());

            this.baseMapper.updateById(todoMemberInfo);
        }else{
            throw new LoginException("密码错误");
        }
    }

    @Override
    public void updateUserName(String userName) {
        if(!StringUtils.hasText(userName)){
            throw new IllegalArgumentException("用户名为空");
        }

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setUid(StpUtil.getLoginId().toString())
                .setUserName(userName)
                .setUpdateTime(LocalDateTime.now());

        this.baseMapper.updateById(memberInfo);
    }

    @Override
    public String updateUserImagePath(MultipartFile file) {
        //上传图片到oss
        ResultObject<String> r = ossFeignService.uploadImage(file);
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        String url = r.getResult();
        String uid = StpUtil.getLoginId().toString();
        MemberInfo memberInfo = this.baseMapper.selectById(uid);
        //删除原图片
        if(memberInfo.getUserImagePath() != null){
            ResultObject<Object> deleteResult = ossFeignService.deleteFile(memberInfo.getUserImagePath());
            if(!deleteResult.getResponseCode().equals(200)){
                throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
            }
        }

        MemberInfo todoUpdateMemberInfo = new MemberInfo();
        todoUpdateMemberInfo.setUid(uid)
                .setUserImagePath(url)
                .setUpdateTime(LocalDateTime.now());

        this.baseMapper.updateById(todoUpdateMemberInfo);

        return url;
    }
}
