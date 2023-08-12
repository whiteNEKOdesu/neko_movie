package neko.movie.nekomovievideo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.entity.RollVideo;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.RollVideoMapper;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.service.RollVideoService;
import neko.movie.nekomovievideo.vo.NewRollVideoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 影视信息轮播图表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-08-12
 */
@Service
public class RollVideoServiceImpl extends ServiceImpl<RollVideoMapper, RollVideo> implements RollVideoService {
    @Resource
    private VideoInfoMapper videoInfoMapper;

    @Resource
    private OSSFeignService ossFeignService;

    /**
     * 添加轮播图信息
     */
    @Override
    public void newRollVideo(NewRollVideoVo vo) {
        VideoInfo videoInfo = videoInfoMapper.selectById(vo.getVideoInfoId());
        if(videoInfo == null){
            throw new NoSuchResultException("无此videoInfoId信息");
        }

        //上传图片到oss
        ResultObject<String> r = ossFeignService.uploadImage(vo.getFile());
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        RollVideo rollVideo = new RollVideo();
        LocalDateTime now = LocalDateTime.now();
        BeanUtil.copyProperties(vo, rollVideo);
        rollVideo.setRollImage(r.getResult())
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(rollVideo);
    }

    /**
     * 获取轮播图信息
     */
    @Override
    public List<RollVideo> getRollVideos() {
        return this.baseMapper.selectList(new QueryWrapper<RollVideo>().lambda()
                .orderByAsc(RollVideo::getSort));
    }

    /**
     * 删除指定rollId轮播图信息
     */
    @Override
    public void deleteRollVideo(Integer rollId) {
        RollVideo rollVideo = this.baseMapper.selectById(rollId);
        if(rollVideo == null){
            return;
        }

        //远程调用thirdparty微服务删除图片
        ResultObject<Object> r = ossFeignService.deleteFile(rollVideo.getRollImage());
        if(!r.getResponseCode().equals(200)){
            throw new ThirdPartyServiceException("thirdparty微服务远程调用异常");
        }

        this.baseMapper.deleteById(rollId);
    }
}
