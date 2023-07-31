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
}
