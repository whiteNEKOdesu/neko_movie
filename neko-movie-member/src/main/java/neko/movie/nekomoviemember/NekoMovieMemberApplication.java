package neko.movie.nekomoviemember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NekoMovieMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(NekoMovieMemberApplication.class, args);
    }

}
