package neko.movie.nekomovievideo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviecommonbase.utils.entity.VideoStatus;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.ThirdPartyServiceException;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.entity.CategoryInfo;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.feign.thirdparty.OSSFeignService;
import neko.movie.nekomovievideo.mapper.VideoInfoMapper;
import neko.movie.nekomovievideo.service.CategoryInfoService;
import neko.movie.nekomovievideo.service.VideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 影视信息表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
@Slf4j
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {
    @Resource
    private OSSFeignService ossFeignService;

    @Resource
    private CategoryInfoService categoryInfoService;

    @Resource
    private ElasticsearchClient elasticsearchClient;

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

    /**
     * 上架影视视频
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upVideo(String videoInfoId) throws IOException {
        VideoInfo videoInfo = this.baseMapper.selectById(videoInfoId);
        if(videoInfo == null){
            throw new NoSuchResultException("无此videoInfoId影视信息");
        }

        //获取分类信息
        CategoryInfo categoryInfo = categoryInfoService.getById(videoInfo.getCategoryId());
        VideoInfo todoVideInfo = new VideoInfo();
        todoVideInfo.setVideoInfoId(videoInfoId)
                .setStatus(VideoStatus.UP);

        this.baseMapper.updateById(todoVideInfo);

        //将影视视频信息收集为elasticsearch形式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        VideoInfoES videoInfoES = new VideoInfoES();
        BeanUtil.copyProperties(videoInfo, videoInfoES);
        videoInfoES.setCategoryName(categoryInfo.getCategoryName())
                .setUpTime(videoInfo.getUpTime().format(dateTimeFormatter));

        //向elasticsearch添加商品信息
        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.operations(operation->operation.index(idx->idx.index(Constant.ELASTIC_SEARCH_INDEX)
                .id(videoInfoId)
                .document(videoInfoES)));
        BulkResponse bulkResponse;
        bulkResponse = elasticsearchClient.bulk(builder.build());

        log.info("影视视频上架videoInfoId: " + videoInfoId + "，" + bulkResponse.toString());
    }
}
