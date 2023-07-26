package neko.movie.nekomovievideo.feign.member;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = ServiceName.MEMBER_SERVICE, contextId = "MemberLevelDict")
public interface MemberLevelDictFeignService {
    @GetMapping("member_level_dict/level_infos")
    ResultObject<List<MemberLevelDictTo>> levelInfos();

    @GetMapping("member_level_dict/role_type_by_member_level_id")
    ResultObject<String> roleTypeByMemberLevelId(@RequestParam Integer memberLevelId);
}
