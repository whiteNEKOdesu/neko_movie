package neko.movie.nekomoviegateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ResultObject<T> {
    private T result;
    private Response responseStatus;
    private Integer responseCode;
    private String responseMessage;

    public static ResultObject<Object> ok(){
        return new ResultObject<Object>()
                .setResponseStatus(Response.SUCCESS)
                .compact();
    }

    public static <T> ResultObject<T> ok(T result){
        return new ResultObject<T>()
                .setResult(result)
                .setResponseStatus(Response.SUCCESS)
                .compact();
    }

    public static ResultObject<Object> unknownError(){
        return new ResultObject<Object>()
                .setResponseStatus(Response.ERROR)
                .compact();
    }

    public static <T> ResultObject<T> unknownError(T result){
        return new ResultObject<T>()
                .setResult(result)
                .setResponseStatus(Response.ERROR)
                .compact();
    }

    public ResultObject<T> tokenError(){
        this.responseStatus = Response.TOKEN_CHECK_ERROR;
        return this;
    }

    public ResultObject<T> compact(){
        Assert.notNull(this.responseStatus,"返回结果未设置responseStatus");
        this.responseCode = this.responseStatus.getResponseCode();
        this.responseMessage = this.responseStatus.getResponseMessage();
        return this;
    }
}
