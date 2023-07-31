package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.MqReturnMessage;
import neko.movie.nekomovievideo.mapper.MqReturnMessageMapper;
import neko.movie.nekomovievideo.service.MqReturnMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * rabbitmq消息发送失败记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Service
public class MqReturnMessageServiceImpl extends ServiceImpl<MqReturnMessageMapper, MqReturnMessage> implements MqReturnMessageService {

}
