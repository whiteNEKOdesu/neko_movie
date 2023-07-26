package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.mapper.OrderInfoMapper;
import neko.movie.nekomovievideo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成 token 保证预生成订单接口幂等性
     */
    @Override
    public String getPreOrderToken() {
        //生成 token 保证接口幂等性
        String token = IdWorker.getTimeId();
        String key = Constant.VIDEO_REDIS_PREFIX + "order_record:" + StpUtil.getLoginId().toString() + token;

        stringRedisTemplate.opsForValue().setIfAbsent(key,
                "neko_movie",
                1000 * 60 * 5,
                TimeUnit.MILLISECONDS);

        return token;
    }
}
