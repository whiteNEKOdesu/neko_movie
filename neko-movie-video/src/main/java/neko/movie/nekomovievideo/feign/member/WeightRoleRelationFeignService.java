package neko.movie.nekomovievideo.feign.member;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import neko.movie.nekomovievideo.to.WeightRoleRelationTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = ServiceName.MEMBER_SERVICE, contextId = "WeightRoleRelation")
public interface WeightRoleRelationFeignService {
    @PostMapping("weight_role_relation/relation_info_by_uid")
    ResultObject<List<WeightRoleRelationTo>> relationInfoByUid(@RequestParam String uid);
}
