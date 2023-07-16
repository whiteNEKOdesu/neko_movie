package neko.movie.nekomovievideo.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AliPayTo implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户订单号 必填
    private String out_trade_no;

    //订单名称 必填
    private String subject;

    //付款金额 必填
    private String total_amount;

    //商品描述 可空
    private String body;
}
