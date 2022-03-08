package org.winterframework.ctrip.apollo.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.winterframework.core.tool.StringTool;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:01 下午
 */
public class CtripApolloDefinitionRegistry implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
    private BeanFactory beanFactory;

    private static final String ENV = "env";

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        // BeanFactory在获取bean失败后，会自动调用bean初始化过程
        ApolloProperties apolloProperties = beanFactory.getBean(ApolloProperties.class);
        String sysEnv = System.getProperty(ENV);
        if (StringTool.isBlank(sysEnv) && StringTool.isNotBlank(apolloProperties.getEnv())) {
            System.setProperty(ENV, apolloProperties.getEnv());
        }
        BeanDefinition beanDefinition = BeanDefinitionBuilder
                .rootBeanDefinition(CtripApolloConfiguration.class)
                .getBeanDefinition();
        registry.registerBeanDefinition("ctripApolloConfiguration", beanDefinition);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
