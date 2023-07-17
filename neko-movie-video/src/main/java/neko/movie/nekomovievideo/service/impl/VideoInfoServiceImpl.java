package neko.movie.nekomovievideo.service.impl;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.service.VideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    @Resource
    private OSSFeignService ossFeignService;

    /**
     * 添加影视信息
     */
    @Override
    public void newVideoInfo(String videoName,
                             String videoDescription,
                             String videoProducer,
                             String videoActors,
                             LocalDateTime upTime,
                             MultipartFile file) {
        //上传图片到oss
        ResultObject<String> r = ossFeignService.uploadImage(file);
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        String videoImage = r.getResult();
        VideoInfo videoInfo = new VideoInfo();
        LocalDateTime now = LocalDateTime.now();
        videoInfo.setVideoName(videoName)
                .setVideoDescription(videoDescription)
                .setVideoImage(videoImage)
                .setVideoProducer(videoProducer)
                .setVideoActors(videoActors)
                .setUpTime(upTime)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(videoInfo);
    }
}
