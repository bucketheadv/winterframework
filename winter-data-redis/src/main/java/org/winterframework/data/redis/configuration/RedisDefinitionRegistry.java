package org.winterframework.data.redis.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.winterframework.data.redis.RedisTemplate;
import org.winterframework.data.redis.props.RedisConfig;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            HostAndPort hostAndPort = new HostAndPort(v.getHost(), v.getPort());
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, hostAndPort, DefaultJedisClientConfig.builder()
                    .password(v.getPassword())
                    .database(v.getDb())
                    .build());
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
