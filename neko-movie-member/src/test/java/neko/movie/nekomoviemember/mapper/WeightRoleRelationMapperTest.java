package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class WeightRoleRelationMapperTest {
    @Resource
    private WeightRoleRelationMapper weightRoleRelationMapper;

    @Test
    public void getRelationsByRoleIds(){
        System.out.println(weightRoleRelationMapper.getRelationsByRoleIds(Arrays.asList(1, 5)));
    }

    @Test
    public void getRelationSbyRoleId(){
        System.out.println(weightRoleRelationMapper.getRelationSbyRoleId(1));
    }
}
