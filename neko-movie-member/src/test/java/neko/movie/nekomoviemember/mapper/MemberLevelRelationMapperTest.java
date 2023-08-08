package neko.movie.nekomoviemember.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class MemberLevelRelationMapperTest {
    @Resource
    private MemberLevelRelationMapper memberLevelRelationMapper;

    @Test
    public void updateExistMemberLevelRelation(){
        memberLevelRelationMapper.updateExistMemberLevelRelation("1684813417543634945",
                LocalDateTime.now(),
                0,
                LocalDateTime.now());
    }

    @Test
    public void getUserRoleRelationByRelationId(){
        System.out.println(memberLevelRelationMapper.getUserRoleRelationByRelationId("1688370680602644482"));
    }

    @Test
    public void getMemberLevelRoleTypesByUid(){
        System.out.println(memberLevelRelationMapper.getMemberLevelRoleTypesByUid("1642067605873348610"));
    }
}
