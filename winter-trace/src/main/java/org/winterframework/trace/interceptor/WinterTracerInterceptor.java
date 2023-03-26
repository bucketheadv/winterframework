package org.winterframework.trace.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.winterframework.core.tool.StringTool;
import org.winterframework.trace.properties.WinterTracerProperties;
import org.winterframework.trace.tool.MDCTool;

/**
 * @author sven
 * Created on 2023/3/24 9:24 PM
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class WinterTracerInterceptor implements HandlerInterceptor {
    private WinterTracerProperties winterTracerProperties;
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String traceIdKey = winterTracerProperties.getTraceId();
        String traceId = MDC.get(traceIdKey);
        if (StringTool.isBlank(traceId)) {
            traceId = request.getHeader(traceIdKey);
            if (StringTool.isBlank(traceId)) {
                traceId = MDCTool.uuid();
            }
        }
        MDC.put(traceIdKey, traceId);
        log.debug("当前请求traceId : {}", traceId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) throws Exception {
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
