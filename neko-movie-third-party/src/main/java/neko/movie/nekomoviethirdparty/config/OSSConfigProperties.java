package neko.movie.nekomoviethirdparty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("neko.alibaba.cloud")
@Component
@Data
public class OSSConfigProperties {
    /**
     * 填写Bucket名称，例如examplebucket
     */
    private String bucket;

    /**
     * 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器
     */
    private String callbackUrl;

    /**
     * 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下
     */
    private String dir;
}
