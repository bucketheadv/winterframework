package org.winterframework.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.tool.JSONTool;
import org.winterframework.gateway.service.UserService;
import org.winterframework.gateway.utils.IpUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author qinglin.liu
 * @create 2023/10/21 12:45
 */
@Slf4j
@Order(-100)
@Component
public class AccessFilter implements GlobalFilter {
    private static final String AUTHORIZATION = "Authorization";

    /**
     * 不需要验证token的地址 网关白名单
     */
    @Value("${api_whitelist:}")
    private Set<String> whiteList;

    @Value("${ip_black_list:}")
    private Set<String> ipBlackList;

    @Autowired
    private UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder requestBuilder = request.mutate();
        String ip = IpUtils.getIpAddress(request);
        requestBuilder.header("ip", ip);
        response.getHeaders().set("ip", ip);
        if (ipBlackList.contains(ip)) {
            log.warn("命中ip黑名单, ip: {}", ip);
            return returnResponse(response, 403, "Forbidden");
        }
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZATION);
        String lang = headers.getFirst("language");
        lang = StringUtils.defaultIfBlank(lang, "en");
        requestBuilder.header("language", lang);

        String requestUri = request.getURI().getPath();
        if (whiteList.contains(requestUri)) {
            log.debug("命中网关url白名单, uri: {}", requestUri);
            if (StringUtils.isNotBlank(token)) {
                Long userId = userService.getUserIdByToken(token);
                requestBuilder.header("userId", String.valueOf(userId));
                return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
            }
            return chain.filter(exchange);
        }
        if (StringUtils.isBlank(token)) {
            log.warn("Token为空，不允许访问，uri: {}", requestUri);
            return returnResponse(response, HttpStatus.UNAUTHORIZED.value(), "Unauthorized!");
        }
        Long userId = userService.getUserIdByToken(token);
        requestBuilder.header("userId", String.valueOf(userId));
        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
    }

    /**
     * 使用webflux输入请求信息
     *
     * @param response
     * @param code
     * @param msg
     * @return
     */
    private Mono<Void> returnResponse(ServerHttpResponse response, Integer code, String msg) {
        ApiResponse<?> message = new ApiResponse<>();
        message.setCode(code);
        message.setMessage(msg);
        byte[] bytes = JSONTool.toJSONString(message).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        // 输出http响应
        return response.writeWith(Mono.just(buffer));
    }
}
