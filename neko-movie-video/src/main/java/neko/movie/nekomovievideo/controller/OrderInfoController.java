package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.service.OrderInfoService;
import neko.movie.nekomovievideo.vo.AliPayAsyncVo;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@RestController
@RequestMapping("order_info")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 获取预生成订单token，保证预生成订单接口幂等性
     */
    @SaCheckLogin
    @GetMapping("preorder_token")
    public ResultObject<String> preorderToken(){
        return ResultObject.ok(orderInfoService.getPreOrderToken());
    }

    /**
     * 提交订单
     */
    @SaCheckLogin
    @PutMapping("new_order")
    public ResultObject<Object> newOrder(@Validated @RequestBody NewOrderInfoVo vo) throws ExecutionException, InterruptedException {
        orderInfoService.newOrder(vo);

        return ResultObject.ok();
    }

    /**
     * 根据订单号获取支付宝支付页面
     */
    @GetMapping(value = "alipay_page", produces = "text/html")
    public String alipayPage(@RequestParam String orderId, @RequestParam String token){
        return orderInfoService.getAlipayPage(orderId, token);
    }

    /**
     * 支付宝异步支付通知处理
     */
    @PostMapping("alipay_listener")
    public String alipayListener(AliPayAsyncVo vo, HttpServletRequest request) throws AlipayApiException {
        return orderInfoService.alipayTradeCheck(vo, request);
    }

    /**
     * 根据订单号获取未取消订单信息
     */
    @GetMapping("order_info_by_order_id")
    public ResultObject<OrderInfo> orderInfoByOrderId(@RequestParam String orderId){
        return ResultObject.ok(orderInfoService.getUncanceledOrderInfoByOrderId(orderId));
    }

    /**
     * 分页查询用户自身订单信息
     */
    @PostMapping("user_self_order_infos")
    public ResultObject<Page<OrderInfo>> userSelfOrderInfos(@Validated @RequestBody QueryVo vo){
        return ResultObject.ok(orderInfoService.getUserSelfOrderInfoByQueryLimitedPage(vo));
    }
}
