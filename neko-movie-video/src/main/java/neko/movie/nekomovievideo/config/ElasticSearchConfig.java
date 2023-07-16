package neko.movie.nekomovievideo.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class ElasticSearchConfig {
    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticSearchConfigProperties elasticSearchConfigProperties){
        // Create the low-level client
        RestClient restClient = RestClient.builder(getHttpHosts(elasticSearchConfigProperties.getHosts()))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> getHttpAsyncClientBuilder(httpAsyncClientBuilder,
                        elasticSearchConfigProperties.getUserName(),
                        elasticSearchConfigProperties.getPassword()))
                .setFailureListener(new RestClient.FailureListener(){
                    @Override
                    public void onFailure(Node node) {
                        log.error("elasticsearch节点: " + node.getName() + "，host: " + node.getHost() + "连接错误");
                    }
                }).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchClient(transport);
    }

    /**
     * 设置连接地址
     */
    private HttpHost[] getHttpHosts(String hostValue){
        Assert.isTrue(StringUtils.hasText(hostValue), "elasticsearch连接地址不能为空");

        String[] hosts = hostValue.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];

        for (int i = 0; i < httpHosts.length; i++) {
            String host = hosts[i].replaceAll("http://", "").replaceAll("https://", "");
            Assert.isTrue(host.contains(":"), "elasticsearch连接地址: " + host + " 格式错误，正确格式如: [127.0.0.1:9200]");
            String[] todoHost = host.split(":");
            httpHosts[i] = new HttpHost(todoHost[0], Integer.parseInt(todoHost[1]));
        }

        return httpHosts;
    }

    /**
     * 设置用户名，密码
     */
    private HttpAsyncClientBuilder getHttpAsyncClientBuilder(HttpAsyncClientBuilder httpAsyncClientBuilder, String userName, String password){
        if(!StringUtils.hasText(userName) && !StringUtils.hasText(password)){
            return httpAsyncClientBuilder;
        }

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

        return httpAsyncClientBuilder;
    }
}
