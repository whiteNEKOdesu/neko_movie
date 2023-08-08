package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class VideoSeriesInfoMapperTest {
    @Resource
    private VideoSeriesInfoMapper videoSeriesInfoMapper;

    @Test
    public void getVideoSeriesInfoForAdminByQueryLimitedPage(){
        System.out.println(videoSeriesInfoMapper.getVideoSeriesInfoForAdminByQueryLimitedPage(8,
                0,
                "1",
                "1680783320826318849"));
    }

    @Test
    public void getVideoSeriesInfoForAdminByQueryLimitedPageNumber(){
        System.out.println(videoSeriesInfoMapper.getVideoSeriesInfoForAdminByQueryLimitedPageNumber("1",
                "1680783320826318849"));
    }

    @Test
    public void getVideoSeriesInfosByVideoInfoId(){
        System.out.println(videoSeriesInfoMapper.getVideoSeriesInfosByVideoInfoId("1683373796746121218"));
    }

    @Test
    public void getVideoWatchHistoryVosByVideoInfoIds(){
        System.out.println(videoSeriesInfoMapper.getVideoWatchHistoryVosByVideoInfoIds(Arrays.asList("1683373881982767105",
                "1683374990189187074")));
    }

    @Test
    public void getVideoWatchHistoryVoByVideoInfoId(){
        System.out.println(videoSeriesInfoMapper.getVideoWatchHistoryVoByVideoInfoId("1683373881982767105"));
    }
}
