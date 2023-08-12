package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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
}
