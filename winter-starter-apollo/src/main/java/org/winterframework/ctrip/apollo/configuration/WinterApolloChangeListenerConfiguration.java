package org.winterframework.ctrip.apollo.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.NonNull;
import org.winterframework.ctrip.apollo.properties.ApolloProperties;

import java.util.*;

/**
 * @author sven
 * Created on 2023/7/17 9:08 AM
 */
@Slf4j
@Configuration
public class WinterApolloChangeListenerConfiguration implements BeanPostProcessor {
    @Autowired
    private ApolloProperties apolloProperties;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;
    @Autowired
    private ConfigurationPropertiesBindingPostProcessor configurationPropertiesBindingPostProcessor;
    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, String> configPrefixBeanNameMapping;

    @PostConstruct
    public void registerApolloConfigChangeListener() {
        if (!apolloProperties.isAutoRefresh()) {
            return;
        }
        log.info("Apollo 开启默认配置刷新。");
        // 从env中拿到所有已从apollo加载的propertySource,获取监听的namespace
        CompositePropertySource apolloPropertySources = (CompositePropertySource) configurableEnvironment.getPropertySources().get("ApolloBootstrapPropertySources");
        if (Objects.isNull(apolloPropertySources)) {
            return;
        }
        Collection<PropertySource<?>> propertySourceList = apolloPropertySources.getPropertySources();
        // 注册监听所有加载的namespace
        for (PropertySource<?> propertySource : propertySourceList) {
            ConfigChangeListener configChangeListener = changeEvent -> {
                for (String changedKey : changeEvent.changedKeys()) {
                    ConfigChange configChange = changeEvent.getChange(changedKey);
                    log.info("apollo配置变更，namespace: {}, key: {}, value: 由 {} 变更为 {}",
                            changeEvent.getNamespace(), changedKey, configChange.getOldValue(), configChange.getNewValue());
                    String beanName = getBeanName(changedKey);
                    if (beanName != null) {
                        configurationPropertiesBindingPostProcessor.postProcessBeforeInitialization(applicationContext.getBean(beanName), beanName);
                    } else {
                        log.warn("获取beanName为空, changedKey: {}", changedKey);
                    }
                }
            };
            Config config = ConfigService.getConfig(propertySource.getName());
            config.addChangeListener(configChangeListener, apolloProperties.getInterestedKeys(), apolloProperties.getInterestedKeyPrefixes());
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        ConfigurationProperties propertiesAnnotation = bean.getClass().getAnnotation(ConfigurationProperties.class);
        if (propertiesAnnotation == null) {
            // 兼容被@Configuration标注，且未指定proxyBeanMethods=false的类
            propertiesAnnotation = bean.getClass().getSuperclass().getAnnotation(ConfigurationProperties.class);
        }
        if (propertiesAnnotation != null) {
            if (configPrefixBeanNameMapping == null) {
                configPrefixBeanNameMapping = Maps.newHashMap();
            }
            String prefix = propertiesAnnotation.prefix() != null ? propertiesAnnotation.prefix() : propertiesAnnotation.value() == null ? null : propertiesAnnotation.value();
            if (!StringUtils.isBlank(prefix) && !StringUtils.isBlank(beanName)) {
                configPrefixBeanNameMapping.put(prefix, beanName);
            }
        }
        return bean;
    }

    private String getBeanName(String key) {
        if (configPrefixBeanNameMapping != null) {
            Optional<Map.Entry<String, String>> bestMatchEntry = configPrefixBeanNameMapping.entrySet().stream()
                    .filter(entry -> key.startsWith(entry.getKey() + "."))
                    .max(Comparator.comparing(Map.Entry<String, String>::getKey));
            return bestMatchEntry.map(Map.Entry::getValue).orElse(null);
        }
        return null;
    }
}
