package neko.movie.nekomoviethirdparty.service.impl;

import neko.movie.nekomoviethirdparty.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendRegisterMail(String emailAddress, String code) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        //发送者
        mimeMessageHelper.setFrom(from);
        //发送给哪个地址
        mimeMessageHelper.setTo(emailAddress);
        //标题
        mimeMessageHelper.setSubject("neko_movie注册邮件");
        //正文
        mimeMessageHelper.setText("\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                "    <link rel=\"icon\" href=\"favicon.ico\">\n" +
                "    <title>发生错误了!!!</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <noscript><strong>We're sorry but nekocloud doesn't work properly without JavaScript enabled. Please enable it to continue.</strong></noscript>\n" +
                "    <div id=\"app\"></div>\n" +
                "    <div style=\"text-align: center\">\n" +
                "      <img style=\"width:15%\" src=\"http://106.15.137.108/static/kokomi4.jpg\">\n" +
                "      <img style=\"width:15%\" src=\"http://106.15.137.108/static/kamisato1.gif\">\n" +
                "      <img style=\"width:15%\" src=\"http://106.15.137.108/static/kamisato2.gif\">\n" +
                "      <img style=\"width:15%\" src=\"http://106.15.137.108/static/kokomi6.gif\">\n" +
                "      <img style=\"width:15%\" src=\"http://106.15.137.108/static/kusakami1.gif\">\n" +
                "      <br/>\n" +
                "      neko_movie注册验证码为 <b>" + code + "</b>，有效时间5分钟，如果非本人操作，请不要处理此验证码" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n", true);

        javaMailSender.send(mimeMessage);
    }
}
