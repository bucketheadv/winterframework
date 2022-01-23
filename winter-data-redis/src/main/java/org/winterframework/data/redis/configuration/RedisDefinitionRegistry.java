package org.winterframework.data.redis.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.winterframework.data.redis.RedisTemplate;
import org.winterframework.data.redis.props.RedisConfig;

/**
 * @author sven
 * Created on 2022/1/14 10:51 下午
 */
public class RedisDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
    private final RedisConfig redisConfig;

    public RedisDefinitionRegistry(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        redisConfig.getTemplate().forEach((k, v) -> {
            RedisClient redisClient = RedisClient.create(RedisURI.builder()
                            .withHost(v.getHost())
                            .withPort(v.getPort())
                    .build());
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RedisTemplate.class)
                    .addConstructorArgValue(redisClient.connect().sync())
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(k + "RedisTemplate", beanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
