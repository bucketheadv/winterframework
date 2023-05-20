package org.winterframework.dynamic.datasource.configuration;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.winterframework.dynamic.datasource.interceptor.DataSourceMethodInterceptor;
import org.winterframework.dynamic.datasource.properties.WinterDataSourceProperties;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:26 PM
 */
@Component
@ComponentScan(basePackages = "org.winterframework.dynamic.datasource")
public class WinterDynamicDataSourceConfiguration implements EnvironmentAware {
    private Environment environment;

    @Bean
    public WinterDataSourceProperties winterDataSourceProperties() {
        return Binder.get(environment).bind("winter.datasource", WinterDataSourceProperties.class).get();
    }

    @Bean
    @ConditionalOnMissingBean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor(WinterDataSourceProperties winterDataSourceProperties) {
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setExpression(winterDataSourceProperties.getExecution());
        pointcutAdvisor.setAdvice(new DataSourceMethodInterceptor());
        return pointcutAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public WinterDynamicDefinitionRegistry winterDynamicDefinitionRegistry(WinterDataSourceProperties winterDataSourceProperties) {
        return new WinterDynamicDefinitionRegistry(winterDataSourceProperties);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
