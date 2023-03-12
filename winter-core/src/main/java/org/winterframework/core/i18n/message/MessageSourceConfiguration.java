package org.winterframework.core.i18n.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author qinglinl
 * Created on 2022/9/26 4:52 PM
 */
@Configuration
public class MessageSourceConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}
}
