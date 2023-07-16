package neko.movie.nekomovievideo.service.impl;

import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.mapper.OrderInfoMapper;
import neko.movie.nekomovievideo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
