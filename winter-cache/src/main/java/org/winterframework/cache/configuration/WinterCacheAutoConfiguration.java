package org.winterframework.cache.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;
import org.winterframework.cache.generator.WinterCacheKeyGenerator;
import org.winterframework.core.tool.StringTool;
import org.winterframework.data.redis.properties.RedisConfig;
import org.winterframework.data.redis.properties.RedisProperties;

import java.time.Duration;
import java.util.Objects;

/**
 * @author qinglinl
 * Created on 2022/10/19 10:35 AM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.cache")
@ConditionalOnProperty(prefix = "spring.cache", value = "type", havingValue = "redis")
public class WinterCacheAutoConfiguration {

	@Autowired
	private RedisConfig redisConfig;

	@Bean
	@ConditionalOnMissingBean
	public WinterCacheKeyGenerator cacheKeyGenerator() {
		return new WinterCacheKeyGenerator();
	}

	@Bean
	@ConditionalOnMissingBean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return new StringRedisTemplate(redisConnectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisProperties redisProperties = redisConfig.getTemplate().get(redisConfig.getPrimary()).getMaster();
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
		redisStandaloneConfiguration.setDatabase(redisProperties.getDb());
		redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
		JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
		GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
		poolConfig.setMaxIdle(redisProperties.getMaxIdle());
		poolConfig.setMaxTotal(redisProperties.getMaxTotal());
		poolConfig.setMinIdle(redisProperties.getMinIdle());
		poolConfig.setJmxEnabled(redisProperties.isJmxEnabled());
		poolConfig.setJmxNameBase(redisProperties.getJmxNameBase());
		poolConfig.setJmxNamePrefix(redisProperties.getJmxNamePrefix());
		poolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
		poolConfig.setTestOnCreate(redisProperties.isTestOnCreate());
		poolConfig.setTestOnReturn(redisProperties.isTestOnReturn());
		poolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
		poolConfig.setMaxWait(Duration.ofSeconds(10));
		poolConfig.setFairness(false);
		jedisClientConfigurationBuilder.usePooling().poolConfig(poolConfig);

		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfigurationBuilder.build());
	}

	@Bean
	@ConditionalOnMissingBean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, String> redisTemplate,
											   ObjectMapper objectMapper) {
		RedisSerializer<?> redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(300))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
		return new DefaultRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
	}

	@Slf4j
	public static class DefaultRedisCacheManager extends RedisCacheManager {
		public DefaultRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
			super(cacheWriter, defaultCacheConfiguration);
		}

		@NonNull
		@Override
		protected RedisCache createRedisCache(@NonNull String name, RedisCacheConfiguration cacheConfig) {
			log.debug("开始创建缓存: {}", name);
			if (StringTool.contains(name, "#")) {
				String[] spEL = name.split("#");
				if (StringTool.isNumeric(spEL[1])) {
					int expires = Integer.parseInt(spEL[1]);
					log.debug("配置缓存 name: {} 到期时间为 {}!!", spEL[0], spEL[1]);
					return super.createRedisCache(spEL[0], cacheConfig.entryTtl(Duration.ofSeconds(expires)));
				}
			}
			return super.createRedisCache(name, cacheConfig);
		}
	}
}
