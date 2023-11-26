package org.winterframework.gateway.confiugration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.winterframework.gateway.filter.GatewayExceptionHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author qinglin.liu
 * created at 2023/11/26 17:56
 */
@Slf4j
public class GatewayExceptionConfig {
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {
        GatewayExceptionHandler gatewayExceptionHandler = new GatewayExceptionHandler();
        gatewayExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        gatewayExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        gatewayExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return gatewayExceptionHandler;
    }
}
