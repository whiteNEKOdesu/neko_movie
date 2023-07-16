package neko.movie.nekomoviecommonbase.utils.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class QueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;

    private Object objectId;

    private String queryWords;

    @NotNull
    @Min(value = 0)
    @Max(value = 50)
    private Integer currentPage;

    @NotNull
    @Min(value = 5)
    @Max(value = 50)
    private Integer limited;

    public Integer daoPage(){
        return (currentPage - 1) * limited;
    }

    public Integer pageOrLimitWhenOverFlow(){
        return currentPage <= 50 ? currentPage : 50;
    }
}
