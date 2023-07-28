package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.DiscountLockLog;
import neko.movie.nekomovievideo.mapper.DiscountLockLogMapper;
import neko.movie.nekomovievideo.service.DiscountLockLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员限时折扣限制数量锁定日志表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Service
public class DiscountLockLogServiceImpl extends ServiceImpl<DiscountLockLogMapper, DiscountLockLog> implements DiscountLockLogService {

    /**
     * 添加库存锁定记录
     */
    @Override
    public void newDiscountLockLog(DiscountLockLog discountLockLog) {
        this.baseMapper.insert(discountLockLog);
    }
}
