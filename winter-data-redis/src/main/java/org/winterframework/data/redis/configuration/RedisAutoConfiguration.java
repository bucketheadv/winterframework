package org.winterframework.data.redis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.winterframework.data.redis.props.RedisConfig;

/**
 * @author sven
 * Created on 2022/1/14 10:25 下午
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "winter.data.redis", value = "enabled", havingValue = "true")
public class RedisAutoConfiguration {
    @Bean
    public RedisConfig redisConfig(Environment env) {
        RedisConfig redisConfig = null;
        try {
            redisConfig = Binder.get(env).bind("winter.data.redis", RedisConfig.class).get();
        } catch (Exception e) {
            log.error("绑定Redis配置失败: ", e);
        }
        return redisConfig;
    }

    @Bean
    public RedisDefinitionRegistry redisDefinitionRegistry(RedisConfig redisConfig) {
        return new RedisDefinitionRegistry(redisConfig);
    }
}
