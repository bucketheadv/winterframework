package org.winterframework.ctrip.apollo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:00 下午
 */
@Configuration
@ConditionalOnProperty(prefix = "apollo.bootstrap", value = "enabled", havingValue = "true")
public class WinterCtripApolloAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public CtripApolloDefinitionRegistry ctripApolloDefinitionRegistry() {
        return new CtripApolloDefinitionRegistry();
    }
}
