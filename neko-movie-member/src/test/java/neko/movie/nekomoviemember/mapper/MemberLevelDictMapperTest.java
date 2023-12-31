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

    @Test
    public void getRoleTypeByMemberLevelId(){
        System.out.println(memberLevelDictMapper.getRoleTypeByMemberLevelId(1));
    }

    @Test
    public void getMemberLevelDictMemberLevelId(){
        System.out.println(memberLevelDictMapper.getMemberLevelDictMemberLevelId(1));
    }

    @Test
    public void getHighestMemberRoleTypeByUid(){
        System.out.println(memberLevelDictMapper.getHighestMemberRoleTypeByUid("1642067605873348610"));
    }
}
