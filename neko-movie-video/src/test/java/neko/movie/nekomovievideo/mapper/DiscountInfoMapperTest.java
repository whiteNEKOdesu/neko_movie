package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class DiscountInfoMapperTest {
    @Resource
    private DiscountInfoMapper discountInfoMapper;

    @Test
    public void getDiscountInfoNearTwoDaysOrAvailable(){
        System.out.println(discountInfoMapper.getDiscountInfoNearTwoDaysOrAvailable());
    }

    @Test
    public void lockStock(){
        discountInfoMapper.lockStock("1684813417543634945",
                1,
                LocalDateTime.now());
    }

    @Test
    public void decreaseStock(){
        discountInfoMapper.decreaseStock("1684813417543634945",
                1,
                LocalDateTime.now());
    }

    @Test
    public void unlockStock(){
        discountInfoMapper.unlockStock("1684813417543634945",
                "202307291554322811685197098011615234",
                LocalDateTime.now());
    }
}
