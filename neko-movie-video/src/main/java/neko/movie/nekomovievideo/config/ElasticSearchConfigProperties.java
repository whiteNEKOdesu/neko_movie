package neko.movie.nekomovievideo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("neko.movie.elasticsearch")
@Component
@Data
public class ElasticSearchConfigProperties {
    private String userName;

    private String password;

    /**
     * 连接地址，如 127.0.0.1:9200,127.0.0.1:9201
     */
    private String hosts;
}
