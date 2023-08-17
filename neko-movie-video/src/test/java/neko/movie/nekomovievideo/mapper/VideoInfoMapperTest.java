package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class VideoInfoMapperTest {
    @Resource
    private VideoInfoMapper videoInfoMapper;

    @Test
    public void getVideoInfoByRootCategoryId(){
        System.out.println(videoInfoMapper.getVideoInfoByRootCategoryId(1, 10));
    }

    @Test
    public void getRecentUpVideoInfo(){
        System.out.println(videoInfoMapper.getRecentUpVideoInfo());
    }

    @Test
    public void increasePlayNumber(){
        videoInfoMapper.increasePlayNumber("1683373796746121218",
                5L,
                LocalDateTime.now());
    }

    @Test
    public void undoDeleteVideoInfo(){
        videoInfoMapper.undoDeleteVideoInfo("1690924812932743169", LocalDateTime.now());
    }
}
