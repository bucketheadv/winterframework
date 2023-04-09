package org.winterframework.rbac.configuration.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RbacPerm {
	/**
	 * 权限url地址
	 * @return String
	 */
	String perm() default "";
}
