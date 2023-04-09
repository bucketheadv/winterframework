package org.winterframework.jwt.interceptor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author qinglinl
 * Created on 2022/9/27 5:46 PM
 */
public class WinterLocaleChangeInterceptor extends LocaleChangeInterceptor {
	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws ServletException {
		String newLocale = request.getHeader(this.getParamName());
		if (newLocale != null && this.checkHttpMethod(request.getMethod())) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}

			try {
				if (localeResolver instanceof AcceptHeaderLocaleResolver) {
					localeResolver.resolveLocale(request);
				} else if (localeResolver instanceof SessionLocaleResolver){
					localeResolver.setLocale(request, response, this.parseLocaleValue(newLocale));
				}
			} catch (IllegalArgumentException e) {
				if (!this.isIgnoreInvalidLocale()) {
					throw e;
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + e.getMessage());
				}
			}
		}
		return true;
	}

	private boolean checkHttpMethod(String currentMethod) {
		String[] configuredMethods = this.getHttpMethods();
		if (ObjectUtils.isEmpty(configuredMethods)) {
			return true;
		}
		for (String configuredMethod : configuredMethods) {
			if (configuredMethod.equalsIgnoreCase(currentMethod)) {
				return true;
			}
		}
		return false;
	}
}
