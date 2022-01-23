package org.winterframework.data.redis.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.winterframework.data.redis.RedisTemplate;
import org.winterframework.data.redis.props.RedisConfig;
import redis.clients.jedis.JedisPool;

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
            JedisPool jedisPool = new JedisPool();
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RedisTemplate.class)
                    .addConstructorArgValue(jedisPool)
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(k + "RedisTemplate", beanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
