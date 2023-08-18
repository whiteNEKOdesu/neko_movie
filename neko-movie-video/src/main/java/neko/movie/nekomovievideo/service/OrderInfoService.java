package neko.movie.nekomovievideo.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.vo.AliPayAsyncVo;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface OrderInfoService extends IService<OrderInfo> {
    String getPreOrderToken();

    void newOrder(NewOrderInfoVo vo) throws ExecutionException, InterruptedException;

    String getAlipayPage(String orderId, String token);

    String alipayTradeCheck(AliPayAsyncVo vo, HttpServletRequest request) throws AlipayApiException;

    void updateOrderInfoStatusToCancel(String orderId);

    OrderInfo getUncanceledOrderInfoByOrderId(String orderId);

    Page<OrderInfo> getUserSelfOrderInfoByQueryLimitedPage(QueryVo vo);
}
