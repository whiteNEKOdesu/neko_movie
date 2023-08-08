package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomoviecommonbase.utils.entity.QueryVo;
import neko.movie.nekomovievideo.entity.VideoCollection;
import neko.movie.nekomovievideo.mapper.VideoCollectionMapper;
import neko.movie.nekomovievideo.service.VideoCollectionService;
import neko.movie.nekomovievideo.vo.VideoCollectionVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    /**
     * 添加影视收藏
     */
    @Override
    public void newVideoCollection(String videoInfoId) {
        //判断是否已经收藏
        if(this.baseMapper.selectOne(new QueryWrapper<VideoCollection>().lambda()
                .eq(VideoCollection::getVideoInfoId, videoInfoId)
                .eq(VideoCollection::getUid, StpUtil.getLoginId())) != null){
            return;
        }

        VideoCollection videoCollection = new VideoCollection();
        LocalDateTime now = LocalDateTime.now();
        videoCollection.setUid(StpUtil.getLoginId().toString())
                .setVideoInfoId(videoInfoId)
                .setCreateTime(now)
                .setUpdateTime(now);

        this.baseMapper.insert(videoCollection);
    }

    /**
     * 删除指定collectionId影视收藏
     */
    @Override
    public void deleteVideoCollection(String collectionId) {
        VideoCollection videoCollection = new VideoCollection();
        videoCollection.setIsDelete(true);

        this.baseMapper.update(videoCollection, new UpdateWrapper<VideoCollection>().lambda()
                .eq(VideoCollection::getCollectionId, collectionId)
                .eq(VideoCollection::getUid, StpUtil.getLoginId().toString()));
    }

    /**
     * 分页查询用户自身收藏信息
     */
    @Override
    public Page<VideoCollectionVo> getUserSelfVideoCollectionByQueryLimitedPage(QueryVo vo) {
        Page<VideoCollectionVo> page = new Page<>(vo.getCurrentPage(), vo.getLimited());
        String uid = StpUtil.getLoginId().toString();
        page.setRecords(this.baseMapper.getUserSelfVideoCollectionByQueryLimitedPage(vo.getLimited(),
                vo.daoPage(),
                vo.getQueryWords(),
                uid));
        page.setTotal(this.baseMapper.getUserSelfVideoCollectionByQueryLimitedPageNumber(vo.getQueryWords(), uid));

        return page;
    }
}
