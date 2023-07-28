package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.MqOrderReturnMessage;
import neko.movie.nekomovievideo.mapper.MqOrderReturnMessageMapper;
import neko.movie.nekomovievideo.service.MqOrderReturnMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单rabbitmq消息发送失败记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Service
public class MqOrderReturnMessageServiceImpl extends ServiceImpl<MqOrderReturnMessageMapper, MqOrderReturnMessage> implements MqOrderReturnMessageService {

}
