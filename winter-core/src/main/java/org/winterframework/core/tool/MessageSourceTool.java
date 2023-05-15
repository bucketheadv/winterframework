package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:50 AM
 */
@UtilityClass
public class MessageSourceTool {

	public static void initMessageSource(MessageSource messageSource, String ...baseNames) {
		if (messageSource instanceof ResourceBundleMessageSource resourceBundleMessageSource) {
			resourceBundleMessageSource.addBasenames(baseNames);
			resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		}
	}
}
