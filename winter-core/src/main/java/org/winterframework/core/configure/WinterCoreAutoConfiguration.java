package org.winterframework.core.configure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2021/12/30 10:56 下午
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.core")
public class WinterCoreAutoConfiguration {
}
