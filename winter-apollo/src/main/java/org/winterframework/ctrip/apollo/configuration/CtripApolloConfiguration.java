package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/7 10:07 上午
 */
@Slf4j
@EnableApolloConfig
public class CtripApolloConfiguration implements ApplicationContextAware, BeanFactoryAware, InitializingBean {
    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    @ApolloConfig
    private Config config;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApolloProperties properties = beanFactory.getBean(ApolloProperties.class);
        if (properties.isEnableDefaultListener()) {
            config.addChangeListener(event -> {
                for (String changedKey : event.changedKeys()) {
                    ConfigChange config = event.getChange(changedKey);
                    log.info("Apollo 配置 {} 由 [{}] 变更为 [{}]", changedKey, config.getOldValue(), config.getNewValue());
                }
                applicationContext.publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
            });
        }
    }
}
