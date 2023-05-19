package org.winterframework.dynamic.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:21 PM
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "winter.datasource")
public class WinterDataSourceProperties {
    /**
     * 默认数据源名称
     */
    private String defaultDataSource = "default";

    /**
     * pointcut 切入点表达式
     */
    private String execution = "execution(* org.*..mapper.*.*(..)) || @annotation(org.winterframework.dynamic.datasource.annotation.DataSource)";

    /**
     * 数据源映射, key是数据源名称, value是数据
     */
    private Map<String, MySqlProperties> dynamic;

    @Getter
    @Setter
    public static class MySqlProperties extends DataSourceProperties {
        private String configLocation;

        private String[] mapperLocations;
    }
}
