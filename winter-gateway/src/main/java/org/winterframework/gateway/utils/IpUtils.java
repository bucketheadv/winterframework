package org.winterframework.gateway.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * @author qinglin.liu
 * @create 2023/10/21 12:47
 */
public class IpUtils {
    /**
     * 获取真实ip
     */
    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (isUnknownIp(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (isUnknownIp(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (isUnknownIp(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (isUnknownIp(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (isUnknownIp(ip)) {
            return Objects.requireNonNull(request.getRemoteAddress()).getHostName();
        }
        ip = StringUtils.defaultIfBlank(ip, "");
        /*处理header中x-forwarded-for可能被重复写入的问题(如x-forwarded-for=103.129.255.70, 103.129.255.70)*/
        if(ip.contains(",")){
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private static boolean isUnknownIp(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
