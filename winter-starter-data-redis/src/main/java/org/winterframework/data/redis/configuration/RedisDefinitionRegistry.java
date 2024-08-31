package org.winterframework.data.redis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.lang.NonNull;
import org.winterframework.core.tool.StringTool;
import org.winterframework.data.redis.core.DefaultJedisTemplate;
import org.winterframework.data.redis.core.JedisTemplate;
import org.winterframework.data.redis.properties.RedisConfig;
import org.winterframework.data.redis.properties.RedisMasterSlaveConfig;
import org.winterframework.data.redis.properties.RedisProperties;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private JedisPool buildJedisPool(RedisProperties v) {
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
        return new JedisPool(jedisPoolConfig, hostAndPort, DefaultJedisClientConfig.builder()
                .password(v.getPassword())
                .database(v.getDb())
                .build());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Map<String, RedisMasterSlaveConfig> templates = redisConfig.getTemplate();
        String primaryKey = templates.containsKey(redisConfig.getPrimary()) ? redisConfig.getPrimary() : null;
        for (String k : templates.keySet()) {
            RedisMasterSlaveConfig v = templates.get(k);

            JedisPool masterPool = buildJedisPool(v.getMaster());
            List<JedisPool> slavePools = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(v.getSlaves())) {
                for (RedisProperties slave : v.getSlaves()) {
                    slavePools.add(buildJedisPool(slave));
                }
            }

            if (StringTool.isBlank(primaryKey)) {
                primaryKey = k;
            }

            boolean primary = k.equals(primaryKey);
            String key = k + JedisTemplate.class.getSimpleName();
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultJedisTemplate.class)
                    .addConstructorArgValue(key)
                    .addConstructorArgValue(masterPool)
                    .addConstructorArgValue(slavePools)
                    .addConstructorArgValue(v.getLoadBalance())
                    .setPrimary(primary)
                    .setDestroyMethodName("close")
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(key, beanDefinition);
            log.info("Bean: {} 注册成功", key);
        }
        log.info("加载redis数据源 {} 个, primary 数据源名称为 [{}]", redisConfig.getTemplate().size(), primaryKey);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
