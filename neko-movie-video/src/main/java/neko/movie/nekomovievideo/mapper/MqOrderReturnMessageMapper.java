package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.MqOrderReturnMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单rabbitmq消息发送失败记录表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Mapper
public interface MqOrderReturnMessageMapper extends BaseMapper<MqOrderReturnMessage> {

}
