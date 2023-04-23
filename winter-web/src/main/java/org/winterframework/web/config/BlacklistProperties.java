package org.winterframework.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author sven
 * Created on 2022/3/6 11:05 下午
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "winter.blacklist")
public class BlacklistProperties {
    private Set<String> users;
}
