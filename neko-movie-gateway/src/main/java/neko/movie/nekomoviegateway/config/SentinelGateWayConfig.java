package neko.movie.nekomoviegateway.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import neko.movie.nekomoviegateway.entity.ResultObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class SentinelGateWayConfig {
    public SentinelGateWayConfig(){
        GatewayCallbackManager.setBlockHandler(((serverWebExchange, throwable) -> {
            ResultObject<Object> resultObject = ResultObject.unknownError();

            Mono<ServerResponse> body = ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(JSONUtil.toJsonStr(resultObject)), String.class);

            return body;
        }));
    }
}
