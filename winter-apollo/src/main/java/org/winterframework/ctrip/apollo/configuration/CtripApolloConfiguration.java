package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * @author qinglinl
 * Created on 2022/3/7 10:07 上午
 */
@Slf4j
@EnableApolloConfig
public class CtripApolloConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @ConditionalOnProperty(
            prefix = "apollo.default-change-listener",
            value = "enabled",
            havingValue = "true"
    )
    @ApolloConfigChangeListener
    public void onConfigChanged(ConfigChangeEvent event) {
        for (String changedKey : event.changedKeys()) {
            ConfigChange config = event.getChange(changedKey);
            log.info("Apollo 配置 {} 由 [{}] 变更为 [{}]", changedKey, config.getOldValue(), config.getNewValue());
        }
        applicationContext.publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
