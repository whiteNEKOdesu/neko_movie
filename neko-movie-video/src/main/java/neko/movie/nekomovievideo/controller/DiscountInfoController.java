package neko.movie.nekomovievideo.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.vo.NewDiscountInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PutMapping("new_discount_info")
    public ResultObject<Object> newDiscountInfo(@Validated @RequestBody NewDiscountInfoVo vo){
        discountInfoService.newDiscountInfo(vo);

        return ResultObject.ok();
    }
}
