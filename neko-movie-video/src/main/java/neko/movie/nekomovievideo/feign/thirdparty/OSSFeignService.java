package neko.movie.nekomovievideo.feign.thirdparty;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = ServiceName.THIRD_PARTY_SERVICE, contextId = "OSS")
public interface OSSFeignService {
    @PostMapping(value = "oss/upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultObject<String> uploadImage(@RequestPart MultipartFile file);

    @DeleteMapping("oss/delete_file")
    ResultObject<Object> deleteFile(@RequestParam String ossFilePath);
}
