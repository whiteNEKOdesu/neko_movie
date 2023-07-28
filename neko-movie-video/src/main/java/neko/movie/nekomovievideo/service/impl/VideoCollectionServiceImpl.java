package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.entity.VideoCollection;
import neko.movie.nekomovievideo.mapper.VideoCollectionMapper;
import neko.movie.nekomovievideo.service.VideoCollectionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-28
 */
@Service
public class VideoCollectionServiceImpl extends ServiceImpl<VideoCollectionMapper, VideoCollection> implements VideoCollectionService {

}
