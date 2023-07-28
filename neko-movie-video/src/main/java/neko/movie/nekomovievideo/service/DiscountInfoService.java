package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.vo.NewDiscountInfoVo;

/**
 * <p>
 * 秒杀折扣信息表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
public interface DiscountInfoService extends IService<DiscountInfo> {
    void newDiscountInfo(NewDiscountInfoVo vo);
}
