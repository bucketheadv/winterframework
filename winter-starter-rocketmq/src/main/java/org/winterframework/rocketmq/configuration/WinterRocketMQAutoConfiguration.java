package org.winterframework.rocketmq.configuration;

import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.winterframework.rocketmq.constants.Const;
import org.winterframework.rocketmq.properties.RocketMQConfig;

/**
 * @author qinglinl
 * Created on 2022/3/2 12:53 下午
 */
@Component
@ConditionalOnProperty(prefix = Const.configPrefix, value = "enabled", havingValue = "true")
public class WinterRocketMQAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Bean
    public RocketMQConfig rocketMQConfig() {
        return Binder.get(environment).bind(Const.configPrefix, RocketMQConfig.class).get();
    }

    @Bean
    @ConditionalOnMissingBean
    public RocketMQMessageConverter messageConverter() {
        return new RocketMQMessageConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public RocketMQBeanDefinition rocketMQBeanDefinition(RocketMQConfig rocketMQConfig, RocketMQMessageConverter messageConverter) {
        return new RocketMQBeanDefinition(rocketMQConfig, messageConverter);
    }

}
