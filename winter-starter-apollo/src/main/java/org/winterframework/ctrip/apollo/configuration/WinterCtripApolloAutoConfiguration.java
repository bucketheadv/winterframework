package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:00 下午
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.winterframework.ctrip.apollo")
public class WinterCtripApolloAutoConfiguration implements InitializingBean {
    @Autowired
    private RefreshScope refreshScope;
    @Autowired
    private ApolloProperties apolloProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (apolloProperties.isEnableAutoRefresh()) {
            log.info("Apollo 开启默认配置刷新。");
            ConfigService.getAppConfig().addChangeListener(configChangeEvent -> {
                for (String changedKey : configChangeEvent.changedKeys()) {
                    ConfigChange configChange = configChangeEvent.getChange(changedKey);
                    log.info("Apollo配置更新, 命名空间: {}, key: {}, 由 {} 变更为 {}。",
                            configChange.getNamespace(), changedKey,
                            configChange.getOldValue(), configChange.getNewValue());
                }
                refreshScope.refreshAll();
            }, apolloProperties.getInterestedKeys(), apolloProperties.getInterestedKeyPrefixes());
        }
    }
}
