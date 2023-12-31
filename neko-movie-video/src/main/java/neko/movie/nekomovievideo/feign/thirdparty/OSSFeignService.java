package neko.movie.nekomovievideo.feign.thirdparty;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = ServiceName.THIRD_PARTY_SERVICE, contextId = "OSS")
public interface OSSFeignService {
    @PostMapping(value = "oss/upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultObject<String> uploadImage(@RequestPart MultipartFile file);

    @DeleteMapping("oss/delete_file")
    ResultObject<Object> deleteFile(@RequestParam String ossFilePath);

    @PostMapping(value = "oss/upload_video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultObject<String> uploadVideo(@RequestPart MultipartFile file);

    @DeleteMapping("oss/delete_file_batch")
    ResultObject<Object> deleteFileBatch(@RequestBody List<String> ossFilePaths);
}
