package org.winterframework.cache.tool;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.winterframework.cache.model.SpELContext;

import java.lang.reflect.Method;

/**
 * @author qinglinl
 * Created on 2022/10/27 2:21 PM
 */
public class SpELTool {
	public static SpELContext parse(Method method, ProceedingJoinPoint proceedingJoinPoint) {
		EvaluationContext context = new StandardEvaluationContext();
		ExpressionParser parser = new SpelExpressionParser();
//		LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
		String[] params = discoverer.getParameterNames(method);
		assert params != null;
		Object[] args = proceedingJoinPoint.getArgs();
		for (int len = 0; len < params.length; len++) {
			context.setVariable(params[len], args[len]);
		}
		return SpELContext.builder()
				.evaluationContext(context)
				.expressionParser(parser)
				.build();
	}
}
