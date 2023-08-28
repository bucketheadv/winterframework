package org.winterframework.trace.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.winterframework.core.tool.StringTool;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.tool.UUIDTool;

/**
 * @author sven
 * Created on 2023/3/24 9:24 PM
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class WinterTracerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String traceIdKey = TraceConstants.TRACE_ID;
        String traceId = MDC.get(traceIdKey);
        if (StringTool.isBlank(traceId)) {
            traceId = request.getHeader(traceIdKey);
            if (StringTool.isBlank(traceId)) {
                traceId = UUIDTool.uuid();
            }
        }
        MDC.put(traceIdKey, traceId);
        String spanId = request.getHeader(TraceConstants.SPAN_ID);
        if (StringUtils.isNotBlank(spanId)) {
            MDC.put(TraceConstants.PARENT_SPAN_ID, spanId);
        }
        MDC.put(TraceConstants.SPAN_ID, UUIDTool.uuid().substring(0, 16));
        log.debug("当前请求traceId : {}", traceId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) throws Exception {
        MDC.remove(TraceConstants.TRACE_ID);
        MDC.remove(TraceConstants.SPAN_ID);
        MDC.remove(TraceConstants.PARENT_SPAN_ID);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
