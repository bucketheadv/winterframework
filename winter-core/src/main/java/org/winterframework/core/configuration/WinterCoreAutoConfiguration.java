package org.winterframework.core.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winterframework.core.tool.MessageSourceTool;

/**
 * @author sven
 * Created on 2021/12/30 10:56 下午
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.core")
public class WinterCoreAutoConfiguration {
	@Autowired(required = false)
	private MessageSource messageSource;

	@PostConstruct
	public void init() {
		MessageSourceTool.initMessageSource(messageSource, "i18n/winter_core_messages");
	}
}
