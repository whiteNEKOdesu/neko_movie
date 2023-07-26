package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class UserRoleMapperTest {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Test
    public void getMemberLevelRoleNumberByRoleIds(){
        System.out.println(userRoleMapper.getMemberLevelRoleNumberByRoleIds(Arrays.asList(11, 12)));
    }
}
