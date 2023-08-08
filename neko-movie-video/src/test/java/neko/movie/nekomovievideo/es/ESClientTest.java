package neko.movie.nekomovievideo.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.elasticsearch.entity.VideoInfoES;
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
}
