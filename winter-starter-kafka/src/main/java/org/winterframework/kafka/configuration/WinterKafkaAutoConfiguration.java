package org.winterframework.kafka.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.winterframework.kafka.constants.Const;
import org.winterframework.kafka.properties.KafkaConfig;

/**
 * @author sven
 * Created on 2022/2/25 10:20 下午
 */
@Component
@ComponentScan(basePackages = "org.winterframework.kafka")
@ConditionalOnProperty(prefix = Const.configPrefix, value = "enabled", havingValue = "true")
public class WinterKafkaAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Bean
    public KafkaConfig kafkaConfig() {
        return Binder.get(environment).bind(Const.configPrefix, KafkaConfig.class).get();
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
