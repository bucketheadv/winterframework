package org.winterframework.data.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.data.redis.constants.Const;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/1/14 10:35 下午
 */

@Getter
@Setter
@ConfigurationProperties(prefix = Const.configPrefix)
public class RedisConfig {
    private boolean enabled = false;
    /**
     * 主redis实例名称
     */
    private String primary;

    /**
     * 配置模板映射
     */
    private Map<String, RedisMasterSlaveConfig> template;
}
