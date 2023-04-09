package org.winterframework.cache.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

/**
 * @author qinglinl
 * Created on 2022/10/27 2:21 PM
 */
@Getter
@Setter
@Builder
public class SpELContext {
	private EvaluationContext evaluationContext;

	private ExpressionParser expressionParser;
}
