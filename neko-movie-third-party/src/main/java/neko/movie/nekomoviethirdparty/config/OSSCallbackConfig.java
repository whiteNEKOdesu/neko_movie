package neko.movie.nekomoviethirdparty.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OSSCallbackConfig {
    private String callbackUrl;

    private String callbackBody;

    private String callbackBodyType = "application/json";
}
