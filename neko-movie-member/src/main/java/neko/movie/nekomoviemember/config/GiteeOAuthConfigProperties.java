package neko.movie.nekomoviemember.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("oauth2.gitee")
@Component
@Data
@Accessors(chain = true)
public class GiteeOAuthConfigProperties {
    private String clientId;

    private String clientSecret;

    private String grantType;

    private String redirectUri;
}
