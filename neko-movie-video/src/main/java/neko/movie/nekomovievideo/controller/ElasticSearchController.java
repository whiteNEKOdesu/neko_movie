package neko.movie.nekomovievideo.controller;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomovievideo.elasticsearch.service.VideoInfoESService;
import neko.movie.nekomovievideo.vo.VideoCategoryAggVo;
import neko.movie.nekomovievideo.vo.VideoInfoESQueryVo;
import neko.movie.nekomovievideo.vo.VideoInfoESVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("elastic_search")
public class ElasticSearchController {
    @Resource
    private VideoInfoESService videoInfoESService;

    /**
     * 分页查询查询影视信息
     */
    @PostMapping("product_infos")
    public ResultObject<VideoInfoESVo> productInfos(@Validated @RequestBody VideoInfoESQueryVo vo) throws IOException {
        return ResultObject.ok(videoInfoESService.getVideoInfoByQueryLimitedPage(vo));
    }

    /**
     * 获取按照影视信息分类聚合信息
     */
    @GetMapping("category_agg")
    public ResultObject<VideoCategoryAggVo> categoryAgg() throws IOException {
        return ResultObject.ok(videoInfoESService.videoCategoryAgg());
    }
}
