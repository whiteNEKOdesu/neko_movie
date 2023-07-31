package neko.movie.nekomovievideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import neko.movie.nekomovievideo.entity.MqReturnMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * rabbitmq消息发送失败记录表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-31
 */
@Mapper
public interface MqReturnMessageMapper extends BaseMapper<MqReturnMessage> {

}
