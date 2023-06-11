package org.winterframework.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.winterframework.security.properties.WinterSecurityProperties;

/**
 * @author sven
 * Created on 2023/6/10 10:37 PM
 */
@Configuration
public class WinterSecurityMvcConfig implements WebMvcConfigurer {
    @Autowired
    private WinterSecurityProperties winterSecurityProperties;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(winterSecurityProperties.getLoginUrl()).setViewName("login");
    }
}
