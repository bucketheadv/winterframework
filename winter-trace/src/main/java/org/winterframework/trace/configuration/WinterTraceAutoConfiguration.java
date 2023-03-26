package org.winterframework.trace.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.winterframework.trace.interceptor.WinterRestTemplateInterceptor;
import org.winterframework.trace.interceptor.WinterTracerInterceptor;

/**
 * @author sven
 * Created on 2023/3/24 9:21 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.trace")
public class WinterTraceAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private WinterTracerInterceptor winterTracerInterceptor;
    @Autowired
    private WinterRestTemplateInterceptor restTemplateInterceptor;
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate.getInterceptors().add(restTemplateInterceptor);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(winterTracerInterceptor);
    }
}
