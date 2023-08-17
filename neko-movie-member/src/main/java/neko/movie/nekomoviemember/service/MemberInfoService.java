package neko.movie.nekomoviemember.service;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviemember.entity.MemberInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.vo.LogInVo;
import neko.movie.nekomoviemember.vo.MemberInfoVo;
import neko.movie.nekomoviemember.vo.ResetUserPasswordVo;
import neko.movie.nekomoviemember.vo.UpdateUserPasswordVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface MemberInfoService extends IService<MemberInfo> {
    ResultObject<MemberInfoVo> logIn(LogInVo vo, HttpServletRequest request);

    void register(String userName, String userPassword, String email, String code);

    boolean userNameIsRepeat(String userName);

    void sendRegisterCode(String email);

    void updateUserPassword(UpdateUserPasswordVo vo);

    void updateUserName(String userName);

    String updateUserImagePath(MultipartFile file);

    MemberInfoVo getUserInfo();

    String sendUserPasswordResetCode(String userName);

    void resetUserPassword(ResetUserPasswordVo vo);
}
