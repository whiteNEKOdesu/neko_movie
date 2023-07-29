package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.StockStatus;
import neko.movie.nekomoviecommonbase.utils.exception.StockNotEnoughException;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.entity.DiscountLockLog;
import neko.movie.nekomovievideo.mapper.DiscountInfoMapper;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.service.DiscountLockLogService;
import neko.movie.nekomovievideo.vo.DiscountInfoVo;
import neko.movie.nekomovievideo.vo.NewDiscountInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀折扣信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Service
@Slf4j
public class DiscountInfoServiceImpl extends ServiceImpl<DiscountInfoMapper, DiscountInfo> implements DiscountInfoService {
    @Resource
    private DiscountLockLogService discountLockLogService;

    /**
     * 添加折扣信息
     */
    @Override
    public void newDiscountInfo(NewDiscountInfoVo vo) {
        LocalDateTime now = LocalDateTime.now();
        //开始时间必须晚于添加时间2天
        if(!now.plusDays(2).isBefore(vo.getStartTime()) || !vo.getStartTime().isBefore(vo.getEndTime())){
            throw new IllegalArgumentException("秒杀折扣开始时间，结束时间非法");
        }

        DiscountInfo discountInfo = new DiscountInfo();
        BeanUtil.copyProperties(vo, discountInfo);
        discountInfo.setOperateAdminUid(StpUtil.getLoginId().toString())
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(discountInfo);
    }

    /**
     * 获取2天内开始或已开始折扣信息
     */
    @Override
    public DiscountInfoVo getDiscountInfoNearTwoDaysOrAvailable() {
        return this.baseMapper.getDiscountInfoNearTwoDaysOrAvailable();
    }

    /**
     * 锁定库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockStock(String discountId, String orderId, Integer lockNumber) {
        LocalDateTime now = LocalDateTime.now();
        //锁定库存
        if(this.baseMapper.lockStock(discountId, lockNumber, now) != 1){
            throw new StockNotEnoughException("库存不足");
        }

        DiscountLockLog discountLockLog = new DiscountLockLog();
        discountLockLog.setOrderId(orderId)
                .setDiscountId(discountId)
                .setLockNumber(lockNumber)
                .setCreateTime(now)
                .setUpdateTime(now);

        //添加库存锁定记录
        discountLockLogService.newDiscountLockLog(discountLockLog);
    }

    /**
     * 解锁指定订单号库存并扣除库存，用于确认支付后扣除库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmLockStockPay(String orderId) {
        DiscountLockLog discountLockLog = discountLockLogService.getById(orderId);
        if(discountLockLog == null){
            log.warn("订单号: " + orderId + "对应库存锁定记录不存在");
            return;
        }

        //修改库存锁定记录状态
        discountLockLogService.updateLockStatus(orderId, StockStatus.PAY);

        //解锁库存并扣除库存
        this.baseMapper.decreaseStock(discountLockLog.getDiscountId(),
                discountLockLog.getLockNumber(),
                LocalDateTime.now());
    }
}
