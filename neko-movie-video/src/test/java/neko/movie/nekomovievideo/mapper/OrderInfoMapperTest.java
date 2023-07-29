package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class OrderInfoMapperTest {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Test
    public void updateOrderInfoStatusToCancel(){
        orderInfoMapper.updateOrderInfoStatusToCancel("202307291554322811685197098011615234", LocalDateTime.now());
    }
}
