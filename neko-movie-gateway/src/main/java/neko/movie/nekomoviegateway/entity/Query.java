package neko.movie.nekomoviegateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Query {
    private Long uid;
    private Object objectId;
    private String queryWords;
    private Integer currentPage;
    private Integer limited;

    public Integer daoPage(){
        return (currentPage-1)*limited;
    }
}
