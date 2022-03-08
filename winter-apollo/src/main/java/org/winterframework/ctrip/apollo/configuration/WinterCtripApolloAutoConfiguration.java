package org.winterframework.ctrip.apollo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:00 下午
 */
@Configuration
@ConditionalOnProperty(prefix = "winter.apollo", value = "enabled", havingValue = "true")
@Import(value = {CtripApolloDefinitionRegistry.class})
public class WinterCtripApolloAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Bean
    public ApolloProperties apolloProperties() {
        return Binder.get(environment).bind("apollo", ApolloProperties.class).get();
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
