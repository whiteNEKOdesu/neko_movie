package neko.movie.nekomovievideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {ElasticsearchRestClientAutoConfiguration.class})
@EnableFeignClients
public class NekoMovieVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NekoMovieVideoApplication.class, args);
    }

}
