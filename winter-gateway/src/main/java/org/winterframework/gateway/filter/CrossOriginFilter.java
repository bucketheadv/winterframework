package org.winterframework.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.winterframework.core.tool.JSONTool;
import reactor.core.publisher.Mono;

/**
 * @author qinglin.liu
 * created at 2023/11/26 18:00
 */
@Slf4j
@Component
@Order(-100)
public class CrossOriginFilter implements GlobalFilter {
    private static final String ALL = "*";
    private static final String MAX_AGE = "18000L";

    @Value("${spring.profiles.active:prod}")
    private String env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (env.equalsIgnoreCase("prod")
                || env.equalsIgnoreCase("production")) {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String requestUrlPath = request.getURI().getPath();
            HttpHeaders headers = response.getHeaders();
            //headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
            //headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                log.info("CrossOriginFilter 网关跨域 OPTIONS 请求返回 requestUrlPath:{},origin:{},headers:{}", requestUrlPath,
                        JSONTool.toJSONString(response.getHeaders()), JSONTool.toJSONString(headers));
                return Mono.empty();
            }
            log.info("CrossOriginFilter 网关跨域 请求返回 requestUrlPath:{},origin:{},headers:{}", requestUrlPath,
                    JSONTool.toJSONString(response.getHeaders()), JSONTool.toJSONString(headers));
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }
}
