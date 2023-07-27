package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.OrderInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
