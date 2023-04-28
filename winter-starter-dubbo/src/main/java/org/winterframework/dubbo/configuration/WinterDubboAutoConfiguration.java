package org.winterframework.dubbo.configuration;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2023/3/26 8:37 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.dubbo")
public class WinterDubboAutoConfiguration implements BeanPostProcessor {
}
