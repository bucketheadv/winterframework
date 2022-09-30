package org.winterframework.rbac.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winterframework.core.tool.MessageSourceTool;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:05 AM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.rbac")
public class WinterRbacAutoConfiguration {
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void init() {
		MessageSourceTool.initMessageSource(messageSource, "i18n/winter_rbac_messages");
	}
}
