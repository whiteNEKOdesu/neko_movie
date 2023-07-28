package neko.movie.nekomovievideo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.RoleType;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.vo.DiscountInfoVo;
import neko.movie.nekomovievideo.vo.NewDiscountInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 秒杀折扣信息表 前端控制器
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@RestController
@RequestMapping("discount_info")
public class DiscountInfoController {
    @Resource
    private DiscountInfoService discountInfoService;

    /**
     * 管理员添加折扣信息
     */
    @SaCheckRole(RoleType.ADMIN)
    @SaCheckLogin
    @PutMapping("new_discount_info")
    public ResultObject<Object> newDiscountInfo(@Validated @RequestBody NewDiscountInfoVo vo){
        discountInfoService.newDiscountInfo(vo);

        return ResultObject.ok();
    }

    /**
     * 获取2天内开始或已开始折扣信息
     */
    @GetMapping("two_days_or_available_discount_info")
    public ResultObject<DiscountInfoVo> twoDaysOrAvailableDiscountInfo(){
        return ResultObject.ok(discountInfoService.getDiscountInfoNearTwoDaysOrAvailable());
    }
}
