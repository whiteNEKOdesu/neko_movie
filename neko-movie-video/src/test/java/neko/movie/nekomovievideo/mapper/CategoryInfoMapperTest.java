package neko.movie.nekomovievideo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CategoryInfoMapperTest {
    @Resource
    private CategoryInfoMapper categoryInfoMapper;

    @Test
    public void deleteLeafCategoryInfo(){
        categoryInfoMapper.deleteLeafCategoryInfo(40);
    }
}
