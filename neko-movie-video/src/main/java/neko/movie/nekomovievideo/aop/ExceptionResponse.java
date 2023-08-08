package neko.movie.nekomovievideo.aop;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.ProfilesActive;
import neko.movie.nekomoviecommonbase.utils.entity.Response;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionResponse {
    @Value("${spring.profiles.active}")
    private String active;

    private Boolean isDebug = true;

    @PostConstruct
    public void init(){
        if(ProfilesActive.PROP.equals(active)){
            isDebug = false;
        }
    }

    //日志方法
    public void exceptionLogger(Exception e){
        if(isDebug){
            debugExceptionLogger(e);
        }else{
            log.error(e.toString());
        }
    }

    public void debugExceptionLogger(Exception e){
        log.error(e.toString());
        for(StackTraceElement trace : e.getStackTrace()){
            log.error(trace.toString());
        }
    }

    //顶级异常处理
    @ExceptionHandler(value = Exception.class)
    public ResultObject<Object> exceptionHandler(Exception e){
        debugExceptionLogger(e);
        return ResultObject.unknownError();
    }

    //非法参数异常处理
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultObject<Object> illegalArgumentExceptionHandler(IllegalArgumentException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ILLEGAL_ARGUMENT_ERROR)
                .compact();
    }

    //文件删除失败异常处理
    @ExceptionHandler(value = FileDeleteFailureException.class)
    public ResultObject<Object> fileDeleteFailureExceptionHandler(FileDeleteFailureException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.FILE_DELETE_FAILURE_ERROR)
                .compact();
    }

    //未找到文件异常
    @ExceptionHandler(value = FileNotFoundException.class)
    public ResultObject<Object> fileNotFoundExceptionHandler(FileNotFoundException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.FILE_NOT_FOUND_ERROR)
                .compact();
    }

    //用户名重复异常
    @ExceptionHandler(value = UserNameRepeatException.class)
    public ResultObject<Object> userNameRepeatExceptionHandler(UserNameRepeatException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.USER_NAME_REPEAT_ERROR)
                .compact();
    }

    //@RequestParam参数缺少异常
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultObject<Object> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ILLEGAL_ARGUMENT_ERROR)
                .compact();
    }

    //@RequestPart参数缺少异常
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResultObject<Object> missingServletRequestPartExceptionHandler(MissingServletRequestPartException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ILLEGAL_ARGUMENT_ERROR)
                .compact();
    }

    //token检查异常
    @ExceptionHandler(value = NotLoginException.class)
    public ResultObject<Object> tokenCheckExceptionHandler(NotLoginException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.TOKEN_CHECK_ERROR)
                .compact();
    }

    //权限不足异常
    @ExceptionHandler(value = NotPermissionException.class)
    public ResultObject<Object> weightNotEnoughExceptionHandler(NotPermissionException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.WEIGHT_NOT_ENOUGH_ERROR)
                .compact();
    }

    //请求类型不支持异常
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultObject<Object> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR)
                .compact();
    }

    //角色不存在异常
    @ExceptionHandler(value = NotRoleException.class)
    public ResultObject<Object> notRoleExceptionHandler(NotRoleException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ROLE_NOT_EXIST_ERROR)
                .compact();
    }

    //JSR303参数验证异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultObject<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ILLEGAL_ARGUMENT_ERROR)
                .compact();
    }

    //重复key异常
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResultObject<Object> duplicateKeyExceptionHandler(DuplicateKeyException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.DUPLICATE_KEY_ERROR)
                .compact();
    }

    //member微服务远程调用异常
    @ExceptionHandler(value = MemberServiceException.class)
    public ResultObject<Object> memberServiceExceptionHandler(MemberServiceException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.MEMBER_SERVICE_ERROR)
                .compact();
    }

    //product微服务远程调用异常
    @ExceptionHandler(value = ProductServiceException.class)
    public ResultObject<Object> productServiceExceptionHandler(ProductServiceException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.PRODUCT_SERVICE_ERROR)
                .compact();
    }

    //参数格式错误异常
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultObject<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ARGUMENT_ILLEGAL_FORMAT_ERROR)
                .compact();
    }

    //邮件发送错误异常
    @ExceptionHandler(value = MailSendException.class)
    public ResultObject<Object> mailSendExceptionHandler(MailSendException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.EMAIL_SEND_ERROR)
                .compact();
    }

    //third_party微服务远程调用异常
    @ExceptionHandler(value = ThirdPartyServiceException.class)
    public ResultObject<Object> thirdPartyServiceExceptionHandler(ThirdPartyServiceException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.THIRD_PARTY_SERVICE_ERROR)
                .compact();
    }

    //邮件已经存在错误
    @ExceptionHandler(value = EMailAlreadyExistException.class)
    public ResultObject<Object> eMailAlreadyExistExceptionHandler(EMailAlreadyExistException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.EMAIL_ALREADY_EXIST_ERROR)
                .compact();
    }

    //验证码错误异常
    @ExceptionHandler(value = CodeIllegalException.class)
    public ResultObject<Object> codeIllegalExceptionHandler(CodeIllegalException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.CODE_ILLEGAL_ERROR)
                .compact();
    }

    //社交登录验证错误异常
    @ExceptionHandler(value = OAuthException.class)
    public ResultObject<Object> oAuthExceptionHandler(OAuthException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.OAUTH_CHECK_ERROR)
                .compact();
    }


    //ware微服务远程调用异常
    @ExceptionHandler(value = WareServiceException.class)
    public ResultObject<Object> wareServiceExceptionHandler(WareServiceException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.WARE_SERVICE_ERROR)
                .compact();
    }

    //无查询结果异常
    @ExceptionHandler(value = NoSuchResultException.class)
    public ResultObject<Object> noSuchResultExceptionHandler(NoSuchResultException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.NO_SUCH_RESULT_ERROR)
                .compact();
    }

    //order微服务远程调用异常
    @ExceptionHandler(value = OrderServiceException.class)
    public ResultObject<Object> orderServiceExceptionHandler(OrderServiceException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ORDER_SERVICE_ERROR)
                .compact();
    }

    //库存不足异常
    @ExceptionHandler(value = StockNotEnoughException.class)
    public ResultObject<Object> stockNotEnoughExceptionHandler(StockNotEnoughException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.STOCK_NOT_ENOUGH_ERROR)
                .compact();
    }

    //rabbitmq消息发送异常
    @ExceptionHandler(value = RabbitMQSendException.class)
    public ResultObject<Object> rabbitMQSendExceptionHandler(RabbitMQSendException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.RABBIT_MQ_SEND_ERROR)
                .compact();
    }

    //库存解锁异常
    @ExceptionHandler(value = StockUnlockException.class)
    public ResultObject<Object> stockUnlockExceptionHandler(StockUnlockException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.STOCK_UNLOCK_ERROR)
                .compact();
    }

    //订单超时异常
    @ExceptionHandler(value = OrderOverTimeException.class)
    public ResultObject<Object> orderOverTimeExceptionHandler(OrderOverTimeException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ORDER_OVER_TIME_ERROR)
                .compact();
    }

    //快递员接单异常
    @ExceptionHandler(value = OrderPickException.class)
    public ResultObject<Object> orderPickExceptionHandler(OrderPickException e){
        exceptionLogger(e);
        return new ResultObject<Object>()
                .setResponseStatus(Response.ORDER_PICK_ERROR)
                .compact();
    }

    //超出数量限制异常
    @ExceptionHandler(value = OutOfLimitationException.class)
    public ResultObject<Object> outOfLimitationExceptionHandler(OutOfLimitationException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.OUT_OF_LIMITATION_ERROR)
                .compact();
    }

    //参数格式错误异常
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResultObject<Object> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.ARGUMENT_ILLEGAL_FORMAT_ERROR)
                .compact();
    }

    //登录错误异常
    @ExceptionHandler(value = LoginException.class)
    public ResultObject<Object> loginExceptionHandler(LoginException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.USER_LOG_IN_ERROR)
                .compact();
    }

    //文件类型不支持异常
    @ExceptionHandler(value = FileTypeNotSupportException.class)
    public ResultObject<Object> fileTypeNotSupportExceptionHandler(FileTypeNotSupportException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.FILE_TYPE_NOT_SUPPORT_ERROR)
                .compact();
    }

    //elasticsearch更新异常
    @ExceptionHandler(value = ElasticSearchUpdateException.class)
    public ResultObject<Object> elasticSearchUpdateExceptionHandler(ElasticSearchUpdateException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.ELASTIC_SEARCH_UPDATE_ERROR)
                .compact();
    }

    //对象仍被使用异常
    @ExceptionHandler(value = ObjectStillUsingException.class)
    public ResultObject<Object> objectStillUsingExceptionHandler(ObjectStillUsingException e){
        exceptionLogger(e);
        return new ResultObject<>()
                .setResponseStatus(Response.OBJECT_STILL_USING_ERROR)
                .compact();
    }
}
