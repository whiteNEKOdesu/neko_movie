package neko.movie.nekomovievideo.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.Data;
import lombok.experimental.Accessors;
import neko.movie.nekomovievideo.to.AliPayTo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
@Accessors(chain = true)
public class AliPayTemplate {
    //在支付宝创建的应用的id
    private String appId;

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchantPrivateKey;;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipayPublicKey;
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notifyUrl;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String returnUrl;

    // 签名方式
    private  String signType;

    // 字符编码格式
    private  String charset;

    //订单超时时间
    private String timeout;

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl;

    private AlipayClient alipayClient;

    @PostConstruct
    public void init(){
        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        alipayClient = new DefaultAlipayClient(gatewayUrl,
                appId, merchantPrivateKey, "json",
                charset, alipayPublicKey, signType);
    }

    public  String pay(AliPayTo to) throws AlipayApiException {
        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = to.getOut_trade_no();
        //付款金额，必填
        String total_amount = to.getTotal_amount();
        //订单名称，必填
        String subject = to.getSubject();
        //商品描述，可空
        String body = to.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayClient.pageExecute(alipayRequest).getBody();
    }
}
