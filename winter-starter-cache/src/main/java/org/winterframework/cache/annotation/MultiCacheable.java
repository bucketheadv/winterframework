package org.winterframework.cache.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiCacheable {
	@AliasFor("prefix")
	String value() default "";

	@AliasFor("value")
	String prefix() default "";

	String key() default "";

	/**
	 * 过期时间
	 * @return
	 */
	int expires() default 1000;

	/**
	 * 表示缓存的维度是根据哪一列生成的
	 * @return
	 */
	String col() default "id";

	/**
	 * ids 参数下标，用于调用方法查询未命中的缓存
	 * @return
	 */
	int idsIndex() default 0;
}
