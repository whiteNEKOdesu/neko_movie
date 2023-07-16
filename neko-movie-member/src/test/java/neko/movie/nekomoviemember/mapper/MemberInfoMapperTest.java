package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MemberInfoMapperTest {
    @Resource
    private MemberInfoMapper memberInfoMapper;

    @Test
    public void getMemberInfoByUserName(){
        System.out.println(memberInfoMapper.getMemberInfoByUserName("NEKO"));
    }
}
