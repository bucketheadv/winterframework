package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:01 下午
 */
@Slf4j
public class CtripApolloDefinitionRegistry implements InitializingBean, EnvironmentAware {
    private Environment environment;
    @Autowired
    private RefreshScope refreshScope;

    @ApolloConfig
    private Config config;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            ApolloProperties properties = Binder.get(environment).bind("apollo", ApolloProperties.class).get();
            if (properties.isEnableDefaultListener()) {
                config.addChangeListener(event -> {
                    for (String changedKey : event.changedKeys()) {
                        ConfigChange config = event.getChange(changedKey);
                        log.info("Apollo 配置 {} 由 [{}] 变更为 [{}]", changedKey, config.getOldValue(), config.getNewValue());
                    }
                    refreshScope.refreshAll();
                });
            }
        } catch (Exception e) {
            log.error("apollo配置绑定失败: {}", e.getMessage());
        }
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
