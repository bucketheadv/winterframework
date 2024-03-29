package org.winterframework.jwt.configuration.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.winterframework.jwt.interceptor.BasedInterceptor;
import org.winterframework.jwt.properties.WinterJwtProperties;

/**
 * @author qinglinl
 * Created on 2022/9/27 5:42 PM
 */
public class JwtMvcConfigurer implements WebMvcConfigurer {
	@Autowired
	private BasedInterceptor basedInterceptor;
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	@Autowired
	private WinterJwtProperties properties;
	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(localeChangeInterceptor);
		if (basedInterceptor != null) {
			registry.addInterceptor(basedInterceptor)
					.addPathPatterns(properties.getPathPatterns())
					.excludePathPatterns(properties.getExcludePathPatterns());
		}
	}
}
