package neko.movie.nekomoviethirdparty.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * oss上传信息
 */
@Data
@Accessors(chain = true)
public class OSSVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessId;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private Long expire;

    private String callbackUrl;

    private String callback;
}
