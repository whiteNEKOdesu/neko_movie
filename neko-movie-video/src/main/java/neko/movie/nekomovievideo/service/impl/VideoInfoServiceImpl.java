package neko.movie.nekomovievideo.service.impl;

import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.service.VideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影视信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {

}
