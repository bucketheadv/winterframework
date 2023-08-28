package org.winterframework.trace.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.tool.UUIDTool;

import java.io.IOException;

/**
 * @author sven
 * Created on 2023/3/24 9:33 PM
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class WinterRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @NonNull
    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request,
                                        @NonNull byte[] body,
                                        @NonNull ClientHttpRequestExecution execution) throws IOException {
        String traceIdKey = TraceConstants.TRACE_ID;
        String traceId = UUIDTool.getOrCreateTraceId(traceIdKey);
        log.debug("RestTemplate设置MDC : {}", traceId);
        request.getHeaders().set(traceIdKey, traceId);

        String spanIdKey = TraceConstants.SPAN_ID;
        String spanId = UUIDTool.getOrCreateTraceId(spanIdKey).substring(0, 16);
        request.getHeaders().set(spanIdKey, spanId);

        return execution.execute(request, body);
    }
}
