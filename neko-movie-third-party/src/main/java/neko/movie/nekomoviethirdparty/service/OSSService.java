package neko.movie.nekomoviethirdparty.service;

import neko.movie.nekomoviethirdparty.vo.OSSCallbackVo;
import neko.movie.nekomoviethirdparty.vo.OSSVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OSSService {
    OSSVo getPolicy();

    void handleCallback(OSSCallbackVo vo);

    String uploadImage(MultipartFile file) throws IOException;

    void deleteFile(String ossFilePath);

    String uploadVideo(MultipartFile file) throws IOException;
}
