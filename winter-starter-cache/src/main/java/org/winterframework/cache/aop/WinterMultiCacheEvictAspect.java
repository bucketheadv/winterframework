package org.winterframework.cache.aop;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;
import org.winterframework.cache.annotation.MultiCacheEvict;
import org.winterframework.cache.generator.WinterCacheKeyGenerator;
import org.winterframework.cache.model.SpELContext;
import org.winterframework.cache.tool.SpELTool;
import org.winterframework.core.tool.CollectionTool;
import org.winterframework.data.redis.core.JedisTemplate;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/27 2:34 PM
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class WinterMultiCacheEvictAspect {
	private WinterCacheKeyGenerator keyGenerator;
	private JedisTemplate jedisTemplate;

	@Pointcut("@annotation(org.winterframework.cache.annotation.MultiCacheEvict)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		MultiCacheEvict multiCacheEvict = method.getAnnotation(MultiCacheEvict.class);

		String prefix = multiCacheEvict.value();

		SpELContext spELContext = SpELTool.parse(method, joinPoint);

		Expression keyExpression = spELContext.getExpressionParser().parseExpression(multiCacheEvict.key());
		List<?> idList = keyExpression.getValue(spELContext.getEvaluationContext(), List.class);
		assert idList != null;

		List<String> keys = Lists.newArrayList();
		for (Object o : idList) {
			String key = keyGenerator.invoke(method, prefix, o);
			keys.add(key);
		}

		Object result = joinPoint.proceed(joinPoint.getArgs());
		if (CollectionTool.isNotEmpty(keys)) {
			jedisTemplate.doInMasterPipeline(pipeline -> {
				for (String key : keys) {
					pipeline.del(key);
				}
			});
		}
		return result;
	}
}
