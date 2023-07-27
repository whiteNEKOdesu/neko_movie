package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

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

    @Test
    public void getUnbindMemberLevelWeightByRoleId(){
        System.out.println(userWeightMapper.getUnbindMemberLevelWeightByRoleId(11));
    }

    @Test
    public void getMemberLevelWeightNumberByWeightIds(){
        System.out.println(userWeightMapper.getMemberLevelWeightNumberByWeightIds(Arrays.asList(11, 12)));
    }

    @Test
    public void getMemberLevelWeightTypeByWeightId(){
        System.out.println(userWeightMapper.getMemberLevelWeightTypeByWeightId(16));
    }
}
