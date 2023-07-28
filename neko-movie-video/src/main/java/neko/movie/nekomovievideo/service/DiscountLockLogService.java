package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.entity.DiscountLockLog;

/**
 * <p>
 * 会员限时折扣限制数量锁定日志表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
public interface DiscountLockLogService extends IService<DiscountLockLog> {
    void newDiscountLockLog(DiscountLockLog discountLockLog);
}
