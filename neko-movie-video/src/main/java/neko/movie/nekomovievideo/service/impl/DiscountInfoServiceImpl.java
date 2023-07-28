package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.mapper.DiscountInfoMapper;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.vo.NewDiscountInfoVo;
import org.springframework.stereotype.Service;

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
public class DiscountInfoServiceImpl extends ServiceImpl<DiscountInfoMapper, DiscountInfo> implements DiscountInfoService {

    /**
     * 添加折扣信息
     */
    @Override
    public void newDiscountInfo(NewDiscountInfoVo vo) {
        if(!vo.getStartTime().isBefore(vo.getEndTime())){
            throw new IllegalArgumentException("秒杀折扣开始时间，结束时间非法");
        }

        DiscountInfo discountInfo = new DiscountInfo();
        BeanUtil.copyProperties(vo, discountInfo);
        LocalDateTime now = LocalDateTime.now();
        discountInfo.setOperateAdminUid(StpUtil.getLoginId().toString())
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(discountInfo);
    }
}
