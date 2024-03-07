package org.winterframework.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author qinglin.liu
 * created at 2024/3/7 14:04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = AutoloadProperties.AUTOLOAD_EXCLUDE)
public class AutoloadProperties {

    public static final String AUTOLOAD_EXCLUDE = "winter.autoload";

    /**
     * 过滤黑名单(支持正则表达式)，黑名单内的Bean将不会加载
     */
    private Set<String> excludes = new LinkedHashSet<>();

    /**
     * 过滤的白名单(支持正则表达式)，白名单内的Bean将必然加载
     */
    private Set<String> includes = new LinkedHashSet<>();
}
