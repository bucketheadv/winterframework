package org.winterframework.core.support;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;

/**
 * @author qinglin.liu
 * created at 2024/3/7 13:46
 */
@Slf4j
@Configuration
public class ApplicationContextHolder implements ApplicationContextInitializer<ConfigurableApplicationContext>,
        EnvironmentPostProcessor, Ordered {
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext context) {
        applicationContext = context;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication application) {
        // 此处可以处理外部传入的环境变量
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
