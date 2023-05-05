package org.winterframework.ctrip.apollo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:16 下午
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "apollo")
public class ApolloProperties {
    /**
     * 是否启用默认配置变更监听器,
     * 启用后当apollo配置修改重新发布时，会自动更新被@ConfigurationProperties和@RefreshScope注解的Bean
     */
    private boolean enableAutoRefresh = true;

    /**
     * 要监听的key全名，支持多个
     */
    private Set<String> interestedKeys;

    /**
     * 要监听的key前缀, 支持多个
     */
    private Set<String> interestedKeyPrefixes;
}
