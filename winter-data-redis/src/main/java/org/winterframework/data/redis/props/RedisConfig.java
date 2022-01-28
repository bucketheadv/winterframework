package org.winterframework.data.redis.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.data.redis.support.Constants;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/1/14 10:35 下午
 */
@Data
@ConfigurationProperties(prefix = Constants.CONFIG_PREFIX)
public class RedisConfig {
    private String primary;

    private Map<String, RedisProperties> template;
}
