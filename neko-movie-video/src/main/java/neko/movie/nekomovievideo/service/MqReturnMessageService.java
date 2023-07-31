package neko.movie.nekomovievideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.entity.MqReturnMessage;

/**
 * <p>
 * rabbitmq消息发送失败记录表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
public interface MqReturnMessageService extends IService<MqReturnMessage> {

}
