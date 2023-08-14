package neko.movie.nekomovievideo.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * elasticsearch索引初始化
 */
@Component
@Slf4j
public class ElasticSearchIndexConfig {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @PostConstruct
    public void init() throws IOException {
        BooleanResponse response = elasticsearchClient.indices().exists(query -> query.index(Constant.ELASTIC_SEARCH_INDEX));

        if(!response.value()){
            log.warn("elasticsearch索引: " + Constant.ELASTIC_SEARCH_INDEX + " 不存在，正在尝试创建索引");

            Assert.isTrue(createIndex(), "elasticsearch索引: " + Constant.ELASTIC_SEARCH_INDEX + " 创建失败");

            log.info("elasticsearch索引: " + Constant.ELASTIC_SEARCH_INDEX + " 创建成功");
        }
    }

    /**
     * 创建索引
     */
    private boolean createIndex() throws IOException {
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
                        .properties("upTime", propertyBuilder -> propertyBuilder.date(dateProperty -> dateProperty.format("yyyy-MM-dd HH:mm:ss")))
                        .properties("playNumber", propertyBuilder -> propertyBuilder.long_(longProperty -> longProperty))));

        return response.acknowledged();
    }
}
