package neko.movie.nekomoviemember.config;

import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RSAConfig {
    @Resource
    private RSAConfigProperties rsaConfigProperties;

    @Bean
    public RSA rsa(){
        return new RSA(rsaConfigProperties.getPrivateKey(), rsaConfigProperties.getPublicKey());
    }
}
