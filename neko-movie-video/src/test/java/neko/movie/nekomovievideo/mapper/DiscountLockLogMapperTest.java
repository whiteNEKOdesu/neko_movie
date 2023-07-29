package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class DiscountLockLogMapperTest {
    @Resource
    private DiscountLockLogMapper discountLockLogMapper;

    @Test
    public void updateStockLockLogStatus(){
        discountLockLogMapper.updateStockLockLogStatus("1650044944645128194",
                Byte.valueOf("1"),
                LocalDateTime.now());
    }
}
