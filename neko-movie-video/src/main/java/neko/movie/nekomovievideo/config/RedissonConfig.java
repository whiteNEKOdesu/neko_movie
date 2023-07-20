package neko.movie.nekomovievideo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {
    /**
     * redisson分布式锁配置
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson(@Value("${spring.redis.host}") String host,
                                   @Value("${spring.redis.port}") String port,
                                   @Value("${spring.redis.password}") String password) {
        //1、创建配置
        //Redis url should start with redis:// or rediss://
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        if(StringUtils.hasText(password)){
            config.useSingleServer().setPassword(password);
        }
        //2、根据Config创建出RedissonClient示例
        return Redisson.create(config);
    }
}
