package neko.movie.nekomovievideo.service;

import neko.movie.nekomovievideo.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;

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

    String getAlipayPage(String orderId);
}
