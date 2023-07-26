package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MemberLevelDictMapperTest {
    @Resource
    private MemberLevelDictMapper memberLevelDictMapper;

    @Test
    public void getMemberLevelDictInfo(){
        System.out.println(memberLevelDictMapper.getMemberLevelDictInfo());
    }
}
