package neko.movie.nekomoviethirdparty.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.exception.FileTypeNotSupportException;
import neko.movie.nekomoviethirdparty.config.OSSCallbackConfig;
import neko.movie.nekomoviethirdparty.config.OSSConfigProperties;
import neko.movie.nekomoviethirdparty.service.OSSService;
import neko.movie.nekomoviethirdparty.vo.OSSCallbackVo;
import neko.movie.nekomoviethirdparty.vo.OSSVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class OSSServiceImpl implements OSSService {
    @Resource
    private OSS ossClient;

    @Resource
    private OSSConfigProperties ossConfigProperties;

    @Value("${alibaba.cloud.access-key}")
    private String accessId;

    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;

    private static final Set<String> imageType = new HashSet<>(Arrays.asList("jpg", "jpeg", "gif", "tiff", "webp", "png"));

    private static final Set<String> videoType = new HashSet<>(Arrays.asList("mp4", "ogg"));

    /**
     * 获取oss上传信息
     */
    @Override
    public OSSVo getPolicy() {
        // 填写Bucket名称，例如examplebucket。
        String bucket = ossConfigProperties.getBucket();
        // 填写Host地址，格式为https://bucketname.endpoint。
        String host = "https://" + bucket + "." + endpoint;
        // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
        String callbackUrl = ossConfigProperties.getCallbackUrl();
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        String dir = ossConfigProperties.getDir() + "/" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "/";

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);
        OSSCallbackConfig callbackConfig = new OSSCallbackConfig();
        callbackConfig.setCallbackUrl(callbackUrl);
        callbackConfig.setCallbackBody("{\"filename\":${object},\"mimeType\":${mimeType},\"size\":${size}}");

        OSSVo ossVo = new OSSVo();
        ossVo.setAccessId(accessId)
                .setPolicy(encodedPolicy)
                .setSignature(postSignature)
                .setDir(dir)
                .setHost(host)
                .setExpire(expireEndTime / 1000)
                .setCallbackUrl(callbackUrl)
                .setCallback(Base64.encode(JSONUtil.toJsonStr(callbackConfig).getBytes(StandardCharsets.UTF_8)));

        return ossVo;
    }

    /**
     * oss回调处理
     */
    @Override
    public void handleCallback(OSSCallbackVo vo) {
        log.info(vo.toString());
    }

    /**
     * oss图片上传
     */
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if(!StringUtils.hasText(fileName) || !imageType.contains(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT))){
            throw new FileTypeNotSupportException("图片类型错误");
        }

        return uploadFile(file);
    }

    /**
     * 删除oss文件
     */
    @Override
    public void deleteFile(String ossFilePath) {
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossConfigProperties.getBucket();
        String objectName = ossFilePath.replace("https://" + bucketName + "." + endpoint + "/", "");

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);
        log.info("文件删除成功，url: " + ossFilePath);
    }

    /**
     * oss视频上传
     */
    @Override
    public String uploadVideo(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if(!StringUtils.hasText(fileName) || !videoType.contains(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT))){
            throw new FileTypeNotSupportException("视频类型错误");
        }

        return uploadFile(file);
    }

    /**
     * 批量删除oss文件
     */
    @Override
    public void deleteFileBatch(List<String> ossFilePaths) {
        if(ossFilePaths.isEmpty()){
            return;
        }

        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossConfigProperties.getBucket();

        for(String ossFilePath : ossFilePaths){
            String objectName = ossFilePath.replace("https://" + bucketName + "." + endpoint + "/", "");

            // 删除文件。
            ossClient.deleteObject(bucketName, objectName);
            log.info("文件删除成功，url: " + ossFilePath);
        }
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // 填写Bucket名称，例如examplebucket。
        String bucket = ossConfigProperties.getBucket();
        String filePath = ossConfigProperties.getDir() + "/" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) +
                "/" + IdUtil.randomUUID() + "_" + file.getOriginalFilename();
        String url = "https://" + bucket + "." + endpoint + "/" + filePath;

        try(InputStream inputStream = file.getInputStream()) {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("文件上传成功，url: " + url + "，eTag: " + result.getETag());
        }

        return url;
    }
}
