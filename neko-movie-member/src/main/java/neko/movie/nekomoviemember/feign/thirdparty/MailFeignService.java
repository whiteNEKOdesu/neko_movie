package neko.movie.nekomoviemember.feign.thirdparty;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceName.THIRD_PARTY_SERVICE, contextId = "Mail")
public interface MailFeignService {
    @PostMapping("mail/send_register_mail")
    ResultObject<Object> sendRegisterMail(@RequestParam String receiver, @RequestParam String code);

    @PostMapping("mail/send_password_reset_mail")
    ResultObject<Object> sendPasswordResetMail(@RequestParam String receiver, @RequestParam String code);
}
