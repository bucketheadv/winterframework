package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:00 下午
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.winterframework.ctrip.apollo")
public class WinterCtripApolloAutoConfiguration implements InitializingBean, EnvironmentAware {
    @Autowired
    private RefreshScope refreshScope;

    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public RefreshScope refreshScope() {
        return new RefreshScope();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApolloProperties apolloProperties = Binder.get(environment).bind("apollo", ApolloProperties.class).get();
        if (apolloProperties.isUseDefaultListener()) {
            log.info("Apollo 开启默认配置刷新。");
            ConfigService.getAppConfig().addChangeListener(configChangeEvent -> {
                for (String changedKey : configChangeEvent.changedKeys()) {
                    ConfigChange configChange = configChangeEvent.getChange(changedKey);
                    log.info("Apollo配置更新, key: {}, 由 {} 变更为 {}。",
                            changedKey, configChange.getOldValue(), configChange.getNewValue());
                }
                refreshScope.refreshAll();
            });
        }
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}