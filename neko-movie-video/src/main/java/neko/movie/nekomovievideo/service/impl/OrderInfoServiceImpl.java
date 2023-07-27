package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.mapper.OrderInfoMapper;
import neko.movie.nekomovievideo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        //生成 token 保证接口幂等性，并可以用来作为订单号
        String token = IdWorker.getTimeId();
        String key = Constant.VIDEO_REDIS_PREFIX + "order_id:" + StpUtil.getLoginId().toString() + token;

        stringRedisTemplate.opsForValue().setIfAbsent(key,
                token,
                1000 * 60 * 5,
                TimeUnit.MILLISECONDS);

        return token;
    }

    @Override
    public String newOrder(NewOrderInfoVo vo) {
        String orderId = vo.getToken();
        String uid = StpUtil.getLoginId().toString();
        String key = Constant.VIDEO_REDIS_PREFIX + "order_id:" + uid + orderId;
        String preOrder = stringRedisTemplate.opsForValue().get(key);
        //删除redis中订单 token
        Boolean isDelete = stringRedisTemplate.delete(key);
        //校验token合法性
        if(isDelete == null || !isDelete || !StringUtils.hasText(preOrder)){
            throw new NoSuchResultException("无此预生成订单信息");
        }

        return null;
    }
}
