package neko.movie.nekomoviethirdparty.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OSSCallbackVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String filename;

    @NotBlank
    private String size;

    @NotBlank
    private String mimeType;
}
