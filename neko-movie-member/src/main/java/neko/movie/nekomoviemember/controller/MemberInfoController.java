package neko.movie.nekomoviemember.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviemember.service.MemberInfoService;
import neko.movie.nekomoviemember.vo.LogInVo;
import neko.movie.nekomoviemember.vo.MemberInfoVo;
import neko.movie.nekomoviemember.vo.ResetUserPasswordVo;
import neko.movie.nekomoviemember.vo.UpdateUserPasswordVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("member_info")
public class MemberInfoController {
    @Resource
    private MemberInfoService memberInfoService;

    @PostMapping("log_in")
    public ResultObject<MemberInfoVo> logIn(@Validated @RequestBody LogInVo vo, HttpServletRequest request){
        return memberInfoService.logIn(vo, request);
    }

    @PostMapping("register")
    public ResultObject<Object> register(@RequestParam String userName,
                                          @RequestParam String userPassword,
                                          @RequestParam String email,
                                          @RequestParam String code){
        memberInfoService.register(userName, userPassword, email, code);

        return ResultObject.ok();
    }

    @GetMapping("user_name_is_repeat")
    public ResultObject<Boolean> userNameIsRepeat(@RequestParam String userName){
        return ResultObject.ok(memberInfoService.userNameIsRepeat(userName));
    }

    @PostMapping("send_register_mail")
    public ResultObject<Object> sendRegisterMail(@RequestParam String mail){
        memberInfoService.sendRegisterCode(mail);
        return ResultObject.ok();
    }

    @SaCheckLogin
    @PostMapping("update_user_password")
    public ResultObject<Object> updateUserPassword(@Validated @RequestBody UpdateUserPasswordVo vo){
        memberInfoService.updateUserPassword(vo);

        return ResultObject.ok();
    }

    @SaCheckLogin
    @PostMapping("update_user_name")
    public ResultObject<Object> updateUserName(@RequestParam String userName){
        memberInfoService.updateUserName(userName);

        return ResultObject.ok();
    }

    @SaCheckLogin
    @PostMapping("update_user_image_path")
    public ResultObject<String> updateUserImagePath(@RequestPart MultipartFile file){
        return ResultObject.ok(memberInfoService.updateUserImagePath(file));
    }

    /**
     * 获取用户基本信息
     */
    @SaCheckLogin
    @GetMapping("user_info")
    public ResultObject<MemberInfoVo> userInfo(){
        return ResultObject.ok(memberInfoService.getUserInfo());
    }

    /**
     * 发送密码重置邮件
     */
    @PostMapping("send_user_password_reset_code")
    public ResultObject<Object> sendPasswordResetCode(@RequestParam String userName){
        return ResultObject.ok(memberInfoService.sendUserPasswordResetCode(userName));
    }

    /**
     * 重置密码
     */
    @PostMapping("reset_user_password")
    public ResultObject<Object> resetUserPassword(@Validated @RequestBody ResetUserPasswordVo vo){
        memberInfoService.resetUserPassword(vo);

        return ResultObject.ok();
    }
}
