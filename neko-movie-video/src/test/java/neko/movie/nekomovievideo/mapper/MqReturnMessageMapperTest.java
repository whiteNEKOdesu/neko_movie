package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
public class MqReturnMessageMapperTest {
    @Resource
    private MqReturnMessageMapper mqReturnMessageMapper;

    @Test
    public void deleteMqReturnMessageByMqReturnIds(){
        mqReturnMessageMapper.deleteMqReturnMessageByMqReturnIds(Arrays.asList("1682551371754180610", "1683373796746121218"),
                LocalDateTime.now());
    }
}
