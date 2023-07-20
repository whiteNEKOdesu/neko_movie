package neko.movie.nekomovievideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.entity.VideoSeriesInfo;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoSeriesInfoMapper;
import neko.movie.nekomovievideo.service.VideoSeriesInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视集数信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class VideoSeriesInfoServiceImpl extends ServiceImpl<VideoSeriesInfoMapper, VideoSeriesInfo> implements VideoSeriesInfoService {
    @Resource
    private OSSFeignService ossFeignService;

    /**
     * 分页查询指定videoSeriesId视频分集信息
     */
    @Override
    public Page<VideoSeriesInfo> getVideoSeriesInfoForAdminByQueryLimitedPage(QueryVo vo) {
        if(vo.getObjectId() == null){
            throw new IllegalArgumentException("缺少参数videoSeriesId");
        }

        Page<VideoSeriesInfo> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        QueryWrapper<VideoSeriesInfo> queryWrapper = new QueryWrapper<>();
        //排除已删除视频信息
        queryWrapper.lambda().eq(VideoSeriesInfo::getVideoInfoId, vo.getObjectId())
                .ne(VideoSeriesInfo::getIsDelete, 1)
                .orderByAsc(VideoSeriesInfo::getSeriesNumber);
        if(StringUtils.hasText(vo.getQueryWords())){
            queryWrapper.lambda().eq(VideoSeriesInfo::getSeriesNumber, vo.getQueryWords());
        }

        this.baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    /**
     * 添加视频分集集数
     */
    @Override
    public void newVideoSeriesInfo(String videoInfoId, Integer seriesNumber, Integer requireLevel, MultipartFile file) {
        //上传视频到oss
        ResultObject<String> r = ossFeignService.uploadVideo(file);
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        String videoUrl = r.getResult();
        VideoSeriesInfo videoSeriesInfo = new VideoSeriesInfo();
        LocalDateTime now = LocalDateTime.now();
        videoSeriesInfo.setVideoInfoId(videoInfoId)
                .setSeriesNumber(seriesNumber)
                .setVideoUrl(videoUrl)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(videoSeriesInfo);
    }
}
