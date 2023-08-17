package neko.movie.nekomoviethirdparty.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviethirdparty.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RestController
@RequestMapping("mail")
public class MailController {
    @Resource
    private MailService mailService;

    @PostMapping("send_register_mail")
    public ResultObject<Object> sendRegisterMail(@RequestParam String receiver, @RequestParam String code) throws MessagingException {
        mailService.sendRegisterMail(receiver, code);
        return ResultObject.ok();
    }

    @PostMapping("send_password_reset_mail")
    public ResultObject<Object> sendPasswordResetMail(@RequestParam String receiver, @RequestParam String code) throws MessagingException {
        mailService.sendPasswordResetMail(receiver, code);
        return ResultObject.ok();
    }
}
