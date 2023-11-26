package org.winterframework.gateway.confiugration;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

/**
 * @author qinglin.liu
 * @create 2023/10/21 12:26
 */
@Slf4j
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        // 允许携带cookie进行跨域
        configuration.setAllowCredentials(true);
        List<String> allowedOriginPatterns = Lists.newArrayList();
        allowedOriginPatterns.add(CorsConfiguration.ALL);
        configuration.setAllowedOriginPatterns(allowedOriginPatterns);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", configuration);
        log.info("==========CorsConfig init success==========");
        return new CorsWebFilter(source);
    }
}
