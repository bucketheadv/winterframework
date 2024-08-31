package org.winterframework.cache.aop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;
import org.winterframework.cache.annotation.MultiCacheable;
import org.winterframework.cache.generator.WinterCacheKeyGenerator;
import org.winterframework.cache.model.SpELContext;
import org.winterframework.cache.tool.ColTool;
import org.winterframework.cache.tool.SpELTool;
import org.winterframework.data.redis.core.JedisTemplate;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/10/24 2:10 PM
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class WinterMultiCacheableAspect {
	private JedisTemplate jedisTemplate;
	private WinterCacheKeyGenerator keyGenerator;

	@Pointcut("@annotation(org.winterframework.cache.annotation.MultiCacheable)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		MultiCacheable multiCacheable = method.getAnnotation(MultiCacheable.class);
		Object[] args = joinPoint.getArgs();

		SpELContext spELContext = SpELTool.parse(method, joinPoint);
		Expression keyExpression = spELContext.getExpressionParser().parseExpression(multiCacheable.key());
		List<?> idList = keyExpression.getValue(spELContext.getEvaluationContext(), List.class);
		assert idList != null;

		String prefix = multiCacheable.prefix();

		RedisSerializer<Object> redisSerializer = new GenericJackson2JsonRedisSerializer();

		List<String> keys = Lists.newArrayList();
		for (Object o : idList) {
			String key = keyGenerator.invoke(method, prefix, o);
			keys.add(key);
		}

		if (CollectionUtils.isEmpty(keys)) {
			return joinPoint.proceed(args);
		}

		List<Object> resultList = jedisTemplate.doInSlavePipeline(pipeline -> {
			for (String key : keys) {
				pipeline.get(key);
			}
		});

		List<Object> missingIdList = Lists.newArrayList();
		Map<Object, String> existsResultMap = Maps.newHashMap();
		for (int i = 0; i < idList.size(); i++) {
			if (resultList.get(i) == null) {
				missingIdList.add(idList.get(i));
			} else {
				existsResultMap.put(idList.get(i), resultList.get(i).toString());
			}
		}

		Class<?> returnClass = method.getReturnType();

		Map<Object, Object> missingResultMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(missingIdList)) {
			args[multiCacheable.idsIndex()] = missingIdList;
			List<?> missingResultList;
			if (Map.class.isAssignableFrom(returnClass)) {
				Map<?, ?> missingResultQueryMap = (Map<?, ?>) method.invoke(joinPoint.getTarget(), args);
				missingResultList = Lists.newArrayList(missingResultQueryMap.values());
			} else if (List.class.isAssignableFrom(returnClass)) {
				missingResultList = (List<?>) method.invoke(joinPoint.getTarget(), args);
			} else {
				throw new RuntimeException("Unsupported Return Type: " + returnClass);
			}
			if (CollectionUtils.isNotEmpty(missingResultList)) {
				jedisTemplate.doInMasterPipeline(pipeline -> {
					for (Object o : missingResultList) {
						Object id = ColTool.getColValue(o, multiCacheable.col());
						missingResultMap.put(id, o);
						String key = keyGenerator.invoke(method, prefix, id);
						byte[] bytes = redisSerializer.serialize(o);
						assert bytes != null;
						pipeline.setex(key, multiCacheable.expires(), new String(bytes, StandardCharsets.UTF_8));
					}
				});
			}
		}

		List<Object> result = Lists.newArrayList();
		for (Object o : idList) {
			String data = existsResultMap.get(o);
			if (data != null) {
				result.add(redisSerializer.deserialize(data.getBytes(StandardCharsets.UTF_8)));
				continue;
			}
			Object mData = missingResultMap.get(o);
			if (mData != null) {
				result.add(mData);
			}
		}

		if (Map.class.isAssignableFrom(returnClass)) {
			return result.stream().collect(Collectors.toMap(i -> ColTool.getColValue(i, multiCacheable.col()), i -> i, (i, j) -> i));
		}
		return result;
	}
}
