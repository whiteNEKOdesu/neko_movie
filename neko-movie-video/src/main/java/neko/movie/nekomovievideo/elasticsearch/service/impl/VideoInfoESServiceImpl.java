package neko.movie.nekomovievideo.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.elasticsearch.service.VideoInfoESService;
import neko.movie.nekomovievideo.vo.VideoCategoryAggVo;
import neko.movie.nekomovievideo.vo.VideoInfoESQueryVo;
import neko.movie.nekomovievideo.vo.VideoInfoESVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VideoInfoESServiceImpl implements VideoInfoESService {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 查询影视信息
     */
    @Override
    public VideoInfoESVo getVideoInfoByQueryLimitedPage(VideoInfoESQueryVo vo) throws IOException {
        //构建查询请求
        SearchRequest request = buildSearchRequest(vo);
        log.info("elasticsearch语句: " + request.toString());
        SearchResponse<VideoInfoES> response = elasticsearchClient.search(buildSearchRequest(vo), VideoInfoES.class);

        //拆解查询响应结果为vo
        VideoInfoESVo searchVo = getSearchVo(response);
        return searchVo.setSize(vo.getLimited())
                .setCurrent(vo.getCurrentPage());
    }

    /**
     * 获取按照影视信息分类聚合饼图信息
     */
    @Override
    public VideoCategoryAggVo videoCategoryAgg() throws IOException {
        Query query = MatchAllQuery.of(m -> m)._toQuery();

        SearchResponse<Void> response = elasticsearchClient.search(b -> b
                        .index(Constant.ELASTIC_SEARCH_INDEX)
                        .size(0)
                        .query(query)
                        .aggregations("categoryTermsAgg", a -> a.terms(h -> h
                                .field("categoryName"))
                                .aggregations("categoryAvgAgg", categoryAvgAgg -> categoryAvgAgg.avg(h -> h
                                        .field("playNumber"))
                                )
                        ),
                Void.class
        );

        List<StringTermsBucket> ageTermsAgg = response.aggregations()
                .get("categoryTermsAgg")
                .sterms()
                .buckets()
                .array();

        VideoCategoryAggVo videoCategoryAggVo = new VideoCategoryAggVo();
        videoCategoryAggVo.setPieVos(new ArrayList<>())
                .setBarVos(new ArrayList<>());

        ageTermsAgg.forEach(bucket -> {
            VideoCategoryAggVo.PieVo pieVo = new VideoCategoryAggVo.PieVo();
            //为饼图vo设置值
            pieVo.setValue(bucket.docCount())
                    .setName(bucket.key().stringValue());

            VideoCategoryAggVo.BarVo barVo = new VideoCategoryAggVo.BarVo();
            //为柱状图vo设置值
            barVo.setValue((long) bucket.aggregations().get("categoryAvgAgg").avg().value())
                    .setName(bucket.key().stringValue());

            videoCategoryAggVo.getPieVos().add(pieVo);
            videoCategoryAggVo.getBarVos().add(barVo);
        });

        return videoCategoryAggVo;
    }

    /**
     * 构建查询请求
     */
    private SearchRequest buildSearchRequest(VideoInfoESQueryVo vo){
        SearchRequest.Builder builder = new SearchRequest.Builder();
        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();

        if(StringUtils.hasText(vo.getVideoInfoId())){
            //按照videoInfoId筛选
            boolBuilder.filter(f ->
                    f.term(t ->
                            t.field("videoInfoId")
                                    .value(vo.getVideoInfoId())));
        }

        if(vo.getCategoryId() != null){
            //按照分类id筛选
            boolBuilder.filter(f ->
                    f.term(t ->
                            t.field("categoryId")
                                    .value(vo.getCategoryId())));
        }

        if(vo.getMinTime() != null && vo.getMaxTime() != null && vo.getMinTime().compareTo(vo.getMaxTime()) < 0){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //按照上映时间范围筛选
            boolBuilder.filter(f ->
                    f.range(r ->
                            r.field("upTime")
                                    .gte(JsonData.of(vo.getMinTime().format(dateTimeFormatter)))
                                    .lte(JsonData.of(vo.getMaxTime().format(dateTimeFormatter)))));
        }

        if(StringUtils.hasText(vo.getQueryWords())){
            boolBuilder.must(m ->
                    m.match(mt ->
                            mt.field("videoDescription")
                                    .query(vo.getQueryWords())))
                    .should(s -> s.match(m -> m.field("videoName").query(vo.getQueryWords())))
                    .should(s -> s.match(m -> m.field("categoryName").query(vo.getQueryWords())))
                    .should(s -> s.match(m -> m.field("videoProducer").query(vo.getQueryWords())))
                    .should(s -> s.match(m -> m.field("videoActors").query(vo.getQueryWords())));
        }

        return builder.index(Constant.ELASTIC_SEARCH_INDEX)
                .query(q ->
                        q.bool(boolBuilder.build()))
                .from(vo.getFrom())
                .size(vo.getLimited())
                .highlight(h ->
                        h.fields("skuTitle", hf -> hf)
                                .preTags("<b style='color:red'>")
                                .postTags("</b>"))
                .build();
    }

    /**
     * 拆解查询响应结果为vo
     */
    private VideoInfoESVo getSearchVo(SearchResponse<VideoInfoES> response){
        VideoInfoESVo videoInfoESVo = new VideoInfoESVo();
        List<VideoInfoES> result = new ArrayList<>();

        List<Hit<VideoInfoES>> hits = response.hits().hits();
        if(hits != null && !hits.isEmpty()){
            for(Hit<VideoInfoES> hit : hits){
                result.add(hit.source());
            }
        }

        return videoInfoESVo.setRecords(result)
                .setTotal(response.hits().total() != null ? Integer.parseInt(response.hits().total().value() + "") : 0);
    }
}
