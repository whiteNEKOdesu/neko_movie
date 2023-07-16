package neko.movie.nekomoviethirdparty.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviethirdparty.service.OSSService;
import neko.movie.nekomoviethirdparty.vo.OSSCallbackVo;
import neko.movie.nekomoviethirdparty.vo.OSSVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("oss")
public class OOSController {
    @Resource
    private OSSService ossService;

    /**
     * 获取oss上传信息
     */
    @SaCheckLogin
    @GetMapping("policy")
    public ResultObject<OSSVo> policy() {
        return ResultObject.ok(ossService.getPolicy());
    }

    /**
     * oss回调处理
     */
    @PostMapping("callback")
    public ResultObject<Object> callback(@Validated @RequestBody OSSCallbackVo vo){
        ossService.handleCallback(vo);

        return ResultObject.ok();
    }

    /**
     * oss图片上传，建议只提供给微服务远程调用
     */
    @PostMapping("upload_image")
    public ResultObject<String> uploadImage(@RequestPart MultipartFile file) throws IOException {
        return ResultObject.ok(ossService.uploadImage(file));
    }

    /**
     * 删除oss文件，建议只提供给微服务远程调用
     */
    @DeleteMapping("delete_file")
    public ResultObject<Object> deleteFile(@RequestParam String ossFilePath){
        ossService.deleteFile(ossFilePath);

        return ResultObject.ok();
    }
}
