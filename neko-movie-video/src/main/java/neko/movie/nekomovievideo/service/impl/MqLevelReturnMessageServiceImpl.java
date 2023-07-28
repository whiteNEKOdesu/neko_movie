package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.MqLevelReturnMessage;
import neko.movie.nekomovievideo.mapper.MqLevelReturnMessageMapper;
import neko.movie.nekomovievideo.service.MqLevelReturnMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 修改会员等级rabbitmq消息发送失败记录表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Service
public class MqLevelReturnMessageServiceImpl extends ServiceImpl<MqLevelReturnMessageMapper, MqLevelReturnMessage> implements MqLevelReturnMessageService {

}
