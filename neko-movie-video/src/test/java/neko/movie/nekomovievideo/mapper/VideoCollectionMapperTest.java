package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class VideoCollectionMapperTest {
    @Resource
    private VideoCollectionMapper videoCollectionMapper;

    @Test
    public void getUserSelfVideoCollectionByQueryLimitedPage(){
        System.out.println(videoCollectionMapper.getUserSelfVideoCollectionByQueryLimitedPage(8,
                0,
                "鬼灭之刃 锻刀村篇",
                "1642067605873348610"));
    }

    @Test
    public void getUserSelfVideoCollectionByQueryLimitedPageNumber(){
        System.out.println(videoCollectionMapper.getUserSelfVideoCollectionByQueryLimitedPageNumber("鬼灭之刃 锻刀村篇",
                "1642067605873348610"));
    }
}
