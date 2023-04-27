package org.winterframework.ctrip.apollo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
     * 是否启用默认配置变更监听器, 启用后会自动更新@ConfigurationProperties注解的Bean
     */
    private boolean useDefaultListener = true;
}
