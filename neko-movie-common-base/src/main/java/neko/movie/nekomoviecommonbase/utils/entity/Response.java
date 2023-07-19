package neko.movie.nekomoviecommonbase.utils.entity;

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
    DUPLICATE_KEY_ERROR(10, "重复key错误"),
    ARGUMENT_ILLEGAL_FORMAT_ERROR(11, "参数格式错误"),
    EMAIL_SEND_ERROR(12, "邮件发送错误"),
    EMAIL_ALREADY_EXIST_ERROR(13, "邮件已经存在错误"),
    CODE_ILLEGAL_ERROR(14, "验证码错误"),
    OAUTH_CHECK_ERROR(15, "社交登录验证错误"),
    APPLY_STATUS_ILLEGAL_ERROR(16, "申请状态非法错误"),
    NO_SUCH_RESULT_ERROR(17, "无查询结果错误"),
    STOCK_NOT_ENOUGH_ERROR(18, "库存不足错误"),
    RABBIT_MQ_SEND_ERROR(19, "rabbitmq消息发送错误"),
    STOCK_UNLOCK_ERROR(20, "库存解锁错误"),
    ORDER_OVER_TIME_ERROR(21, "订单超时错误"),
    ORDER_PICK_ERROR(22, "快递员接单错误"),
    OUT_OF_LIMITATION_ERROR(23, "超出数量限制错误"),
    FILE_TYPE_NOT_SUPPORT_ERROR(24, "文件类型不支持错误"),
    ELASTIC_SEARCH_UPDATE_ERROR(25, "elasticsearch更新错误"),

    MEMBER_SERVICE_ERROR(1000, "member微服务远程调用错误"),
    PRODUCT_SERVICE_ERROR(1001, "product微服务远程调用错误"),
    THIRD_PARTY_SERVICE_ERROR(1002, "third_party微服务远程调用错误"),
    WARE_SERVICE_ERROR(1003, "ware微服务远程调用错误"),
    ORDER_SERVICE_ERROR(1004, "order微服务远程调用错误"),

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
