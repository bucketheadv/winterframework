package org.winterframework.gateway.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author qinglin.liu
 * @create 2023/10/21 12:40
 */
@Order(-1)
@Component
public class HttpRequestMDCFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String lang = headers.getFirst("language");
        String deviceId = headers.getFirst("deivce-id");
        String country = headers.getFirst("country");
        String userId = headers.getFirst("userId");
        MDC.put("lang", lang);
        MDC.put("deviceId", deviceId);
        MDC.put("country", country);
        MDC.put("userId", userId);
        return chain.filter(exchange);
    }
}
