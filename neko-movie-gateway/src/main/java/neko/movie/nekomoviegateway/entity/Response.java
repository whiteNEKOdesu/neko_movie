package neko.movie.nekomoviegateway.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum Response {
    SUCCESS(200,"ok"),

    ILLEGAL_ARGUMENT_ERROR(1,"缺少参数或参数错误"),

    FILE_NOT_FOUND_ERROR(2,"文件不存在"),
    FILE_DELETE_FAILURE_ERROR(3,"文件删除失败"),

    TOKEN_CHECK_ERROR(4,"token验证失败"),

    WEIGHT_NOT_ENOUGH_ERROR(5,"权限不足"),
    USER_NAME_REPEAT_ERROR(6,"用户名重复"),
    ROLE_NOT_EXIST_ERROR(7, "角色不存在"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR(8, "请求类型不支持"),
    USER_LOG_IN_ERROR(9, "登录错误"),

    ERROR(500,"未知错误");

    private Integer responseCode;
    private String responseMessage;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
