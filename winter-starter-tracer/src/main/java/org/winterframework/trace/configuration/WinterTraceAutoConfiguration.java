package org.winterframework.trace.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.interceptor.WinterRestTemplateInterceptor;
import org.winterframework.trace.interceptor.WinterTracerInterceptor;
import org.winterframework.trace.tool.UUIDTool;

import java.time.Duration;

/**
 * @author sven
 * Created on 2023/3/24 9:21 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.trace")
@Slf4j
public class WinterTraceAutoConfiguration implements WebMvcConfigurer, SpringApplicationRunListener {
    @Autowired
    private WinterTracerInterceptor winterTracerInterceptor;
    @Autowired
    private WinterRestTemplateInterceptor restTemplateInterceptor;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        MDC.put(TraceConstants.TRACE_ID, UUIDTool.uuid());
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        MDC.clear();
    }

    @PostConstruct
    public void init() {
        restTemplate.getInterceptors().add(restTemplateInterceptor);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(winterTracerInterceptor);
    }
}
