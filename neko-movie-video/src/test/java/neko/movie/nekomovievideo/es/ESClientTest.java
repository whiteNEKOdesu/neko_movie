package neko.movie.nekomovievideo.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomoviecommonbase.utils.entity.VideoStatus;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
import neko.movie.nekomovievideo.entity.VideoInfo;
import neko.movie.nekomovievideo.service.VideoInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ESClientTest {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private VideoInfoService videoInfoService;

    @Test
    public void upProduct() throws IOException {
        List<VideoInfoES> videoInfoESs = new ArrayList<>();
        videoInfoESs.add(new VideoInfoES().setVideoInfoId("1680781825485639682")
                .setVideoName("NEO")
                .setVideoDescription("NEKO")
                .setVideoImage("https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg")
                .setCategoryId(34)
                .setCategoryName("校园")
                .setVideoProducer("NEKO")
                .setVideoActors("NEKO")
                .setUpTime("2023-07-17 11:29:49"));
        videoInfoESs.add(new VideoInfoES().setVideoInfoId("1680783130899845122")
                .setVideoName("NEO")
                .setVideoDescription("NEKO")
                .setVideoImage("https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg")
                .setCategoryId(34)
                .setCategoryName("校园")
                .setVideoProducer("NEKO")
                .setVideoActors("NEKO")
                .setUpTime("2023-07-17 11:29:49"));
        BulkRequest.Builder builder = new BulkRequest.Builder();
        for(VideoInfoES videoInfoES : videoInfoESs){
            builder.operations(operation->operation.index(idx->idx.index("neko_movie")
                    .id(videoInfoES.getVideoInfoId())
                    .document(videoInfoES)));
        }
        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
        System.out.println(bulkResponse);
    }

    @Test
    public void downSingleVideo() throws IOException {
        DeleteResponse response = elasticsearchClient.delete(builder ->
                builder.index("neko_movie")
                        .id("5"));
        System.out.println(response.shards().successful().intValue());
    }

    @Test
    public void downMultiplyVideo() throws IOException {
        DeleteByQueryResponse response = elasticsearchClient.deleteByQuery(builder ->
                builder.index("neko_movie")
                        .query(q ->
                                q.term(t ->
                                        t.field("videoInfoId")
                                                .value(5))));
        System.out.println(response.deleted());
    }

    @Test
    public void getIndex() throws IOException {
        BooleanResponse response = elasticsearchClient.indices().exists(query -> query.index(Constant.ELASTIC_SEARCH_INDEX));
        System.out.println(response.value());
    }

    @Test
    public void createIndex() throws IOException {
        CreateIndexResponse response = elasticsearchClient.indices().create(builder -> builder.index(Constant.ELASTIC_SEARCH_INDEX)
                .mappings(map -> map
                        .properties("videoInfoId", propertyBuilder -> propertyBuilder.long_(longProperty -> longProperty))
                        .properties("videoName", propertyBuilder -> propertyBuilder.keyword(keyWordProperty -> keyWordProperty))
                        .properties("videoDescription", propertyBuilder -> propertyBuilder.text(textProperty ->
                                textProperty.analyzer("ik_smart").searchAnalyzer("ik_smart")))
                        .properties("videoImage", propertyBuilder -> propertyBuilder.keyword(keyWordProperty -> keyWordProperty))
                        .properties("categoryId", propertyBuilder -> propertyBuilder.long_(longProperty -> longProperty))
                        .properties("categoryName", propertyBuilder -> propertyBuilder.text(textProperty ->
                                textProperty.analyzer("ik_smart").searchAnalyzer("ik_smart")))
                        .properties("videoProducer", propertyBuilder -> propertyBuilder.keyword(keyWordProperty -> keyWordProperty))
                        .properties("videoActors", propertyBuilder -> propertyBuilder.text(textProperty ->
                                textProperty.analyzer("ik_smart").searchAnalyzer("ik_smart")))
                        .properties("upTime", propertyBuilder -> propertyBuilder.date(dateProperty -> dateProperty.format("yyyy-MM-dd HH:mm:ss")))));
        System.out.println(response.acknowledged());
    }

    @Test
    public void videoCategoryAggTest() throws IOException {
        Query query = MatchAllQuery.of(m -> m)._toQuery();

        SearchResponse<Void> response = elasticsearchClient.search(b -> b
                        .index(Constant.ELASTIC_SEARCH_INDEX)
                        .size(0)
                        .query(query)
                        .aggregations("categoryTermsAgg", a -> a.terms(h -> h
                                .field("categoryName"))
                        ),
                Void.class
        );

        List<StringTermsBucket> ageTermsAgg = response.aggregations()
                .get("categoryTermsAgg")
                .sterms()
                .buckets()
                .array();

        for (StringTermsBucket bucket: ageTermsAgg) {
            System.out.println("分类: " + bucket.key().stringValue() + " 有" + bucket.docCount() +
                    " 个影视视频");
        }
    }

    @Test
    public void upAll() throws IOException {
        List<VideoInfo> videoInfos = videoInfoService.getBaseMapper().selectList(new QueryWrapper<VideoInfo>().lambda()
                .ne(VideoInfo::getStatus, VideoStatus.LOGIC_DELETE)
                .ne(VideoInfo::getStatus, VideoStatus.DELETED));
        for(VideoInfo videoInfo : videoInfos){
            videoInfoService.upVideo(videoInfo.getVideoInfoId());
        }
    }
}
