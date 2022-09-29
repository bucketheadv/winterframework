package org.winterframework.jwt.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.winterframework.jwt.configuration.mvc.JwtMvcConfigurer;
import org.winterframework.jwt.properties.WinterJwtProperties;
import org.winterframework.jwt.interceptor.BasedInterceptor;
import org.winterframework.jwt.interceptor.WinterLocaleChangeInterceptor;

/**
 * @author qinglinl
 * Created on 2022/9/27 1:55 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.jwt")
public class WinterJwtAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired(required = false)
	private MessageSource messageSource;
	@Autowired
	private WinterJwtProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public JwtMvcConfigurer jwtMvcConfigurer() {
		return new JwtMvcConfigurer();
	}

	@Bean
	@ConditionalOnMissingBean
	public BasedInterceptor basedInterceptor() {
		return new BasedInterceptor();
	}

	@Bean
	@ConditionalOnMissingBean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new WinterLocaleChangeInterceptor();
	}

	@PostConstruct
	public void init() {
		if (messageSource instanceof ResourceBundleMessageSource) {
			ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) messageSource;
			resourceBundleMessageSource.addBasenames("i18n/winter_jwt_messages");
			resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		AcceptHeaderLocaleResolver resolver = event.getApplicationContext().getBean(AcceptHeaderLocaleResolver.class);
		resolver.setSupportedLocales(properties.getSupportLocales());
		resolver.setDefaultLocale(properties.getDefaultLocale());
	}
}
