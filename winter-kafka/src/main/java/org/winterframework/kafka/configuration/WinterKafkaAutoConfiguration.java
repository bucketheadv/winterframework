package org.winterframework.kafka.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.winterframework.kafka.properties.KafkaConfig;

/**
 * @author sven
 * Created on 2022/2/25 10:20 下午
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.kafka")
@ConditionalOnProperty(prefix = "winter.data.kafka", value = "enabled", havingValue = "true", matchIfMissing = true)
public class WinterKafkaAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Bean
    public KafkaConfig kafkaConfig() {
        return Binder.get(environment).bind("winter.data.kafka", KafkaConfig.class).get();
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaDefinitionRegistry kafkaDefinitionRegistry(KafkaConfig kafkaConfig) {
        return new KafkaDefinitionRegistry(kafkaConfig);
    }

    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
