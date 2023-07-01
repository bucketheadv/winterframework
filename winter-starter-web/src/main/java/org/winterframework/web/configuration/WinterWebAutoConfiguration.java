package org.winterframework.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2023/7/1 12:29 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.web")
public class WinterWebAutoConfiguration {
}
