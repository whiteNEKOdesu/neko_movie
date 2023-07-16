package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserRoleRelationMapperTest {
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Test
    public void getRoleIdsByUid(){
        System.out.println(userRoleRelationMapper.getRoleIdsByUid("1642067605873348610"));
    }
}
