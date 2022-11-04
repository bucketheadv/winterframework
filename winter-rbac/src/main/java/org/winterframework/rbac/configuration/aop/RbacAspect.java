package org.winterframework.rbac.configuration.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.winterframework.core.tool.StringTool;
import org.winterframework.jwt.interceptor.EnvironmentHolder;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;
import org.winterframework.rbac.exception.RbacPermDeniedException;
import org.winterframework.rbac.service.RbacService;

import java.lang.reflect.Method;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:23 AM
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@Order(0)
public class RbacAspect {
	private RbacService rbacService;
	@Pointcut("@annotation(org.winterframework.rbac.configuration.aop.annotation.RbacPerm)")
	public void pointcut() {}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		RbacPerm rbacPerm = method.getAnnotation(RbacPerm.class);

		String perm = rbacPerm.perm();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		assert attributes != null;
		HttpServletRequest request = attributes.getRequest();
		String uri = request.getRequestURI();
		perm = StringTool.defaultIfBlank(perm, uri);

		Long userId = EnvironmentHolder.get().getUid();
		boolean hasPerm = rbacService.hasUserPermForUrl(userId, perm);
		if (!hasPerm) {
			log.warn("用户 {} 尝试获取 {} 的权限失败!", userId, perm);
			String msg = String.format("user %s has no perm %s", userId, perm);
			throw new RbacPermDeniedException(msg);
		}
		return joinPoint.proceed(joinPoint.getArgs());
	}
}
