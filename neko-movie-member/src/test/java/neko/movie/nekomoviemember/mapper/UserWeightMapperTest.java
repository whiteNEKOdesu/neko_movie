package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserWeightMapperTest {
    @Resource
    private UserWeightMapper userWeightMapper;

    @Test
    public void getUserWeightByWeightType(){
        System.out.println(userWeightMapper.getUserWeightByWeightType("*"));
    }

    @Test
    public void getUnbindUserWeightByRoleId(){
        System.out.println(userWeightMapper.getUnbindUserWeightByRoleId(1));
    }
}
