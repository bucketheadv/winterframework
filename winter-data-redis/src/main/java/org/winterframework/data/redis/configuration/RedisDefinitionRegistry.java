package org.winterframework.data.redis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.winterframework.data.redis.RedisTemplate;
import org.winterframework.data.redis.props.RedisConfig;
import org.winterframework.data.redis.props.RedisProperties;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author sven
 * Created on 2022/1/14 10:51 下午
 */
@Slf4j
public class RedisDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
    private final RedisConfig redisConfig;

    public RedisDefinitionRegistry(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String primaryKey = null;
        for (String k : redisConfig.getTemplate().keySet()) {
            RedisProperties v = redisConfig.getTemplate().get(k);
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setTestOnReturn(v.isTestOnReturn());
            jedisPoolConfig.setTestOnBorrow(v.isTestOnBorrow());
            jedisPoolConfig.setTestOnCreate(v.isTestOnCreate());
            jedisPoolConfig.setTestWhileIdle(v.isTestWhileIdle());
            jedisPoolConfig.setJmxEnabled(v.isJmxEnabled());
            jedisPoolConfig.setJmxNameBase(v.getJmxNameBase());
            jedisPoolConfig.setJmxNamePrefix(v.getJmxNamePrefix());
            jedisPoolConfig.setMaxIdle(v.getMaxIdle());
            jedisPoolConfig.setMaxTotal(v.getMaxTotal());
            jedisPoolConfig.setMinIdle(v.getMinIdle());

            HostAndPort hostAndPort = new HostAndPort(v.getHost(), v.getPort());
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, hostAndPort, DefaultJedisClientConfig.builder()
                    .password(v.getPassword())
                    .database(v.getDb())
                    .build());
            boolean primary = k.equals(redisConfig.getPrimary());
            String key = k + "RedisTemplate";
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RedisTemplate.class)
                    .addConstructorArgValue(key)
                    .addConstructorArgValue(jedisPool)
                    .setPrimary(primary)
                    .setDestroyMethodName("close")
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(key, beanDefinition);
            if (primary) {
                primaryKey = key;
            }
            log.info("Bean: {} 注册成功", key);
        }
        log.info("加载redis数据源 {} 个, primary 数据源名称为 [{}]", redisConfig.getTemplate().size(), primaryKey);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
