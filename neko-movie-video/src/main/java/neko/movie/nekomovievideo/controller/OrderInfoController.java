package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.OrderInfoService;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @PutMapping("new_order")
    public ResultObject<Object> newOrder(@Validated @RequestBody NewOrderInfoVo vo) throws ExecutionException, InterruptedException {
        orderInfoService.newOrder(vo);

        return ResultObject.ok();
    }

    /**
     * 根据订单号获取支付宝支付页面
     */
    @GetMapping(value = "alipay_page", produces = "text/html")
    public String alipayPage(@RequestParam String orderId){
        return orderInfoService.getAlipayPage(orderId);
    }
}
