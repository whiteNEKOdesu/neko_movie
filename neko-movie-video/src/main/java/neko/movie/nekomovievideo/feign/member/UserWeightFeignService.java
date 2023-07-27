package neko.movie.nekomovievideo.feign.member;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import neko.movie.nekomovievideo.to.UserWeightTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = ServiceName.MEMBER_SERVICE, contextId = "UserWeight")
public interface UserWeightFeignService {
    @GetMapping("user_weight/member_level_weight_infos")
    ResultObject<List<UserWeightTo>> memberLevelWeightInfos();

    @GetMapping("user_weight/member_level_weight_name_by_weight_id")
    ResultObject<String> memberLevelWeightNameByWeightId(@RequestParam Integer weightId);
}
