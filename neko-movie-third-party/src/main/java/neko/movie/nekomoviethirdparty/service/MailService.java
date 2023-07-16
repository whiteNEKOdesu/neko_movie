package neko.movie.nekomoviethirdparty.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendRegisterMail(String emailAddress, String code) throws MessagingException;
}
