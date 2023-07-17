package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AdminInfoMapperTest {
    @Resource
    private AdminInfoMapper adminInfoMapper;

    @Test
    public void getAdminInfoByUserName(){
        System.out.println(adminInfoMapper.getAdminInfoByUserName("NEKO"));
    }
}
