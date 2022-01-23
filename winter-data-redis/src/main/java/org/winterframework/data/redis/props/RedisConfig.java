package org.winterframework.data.redis.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/1/14 10:35 下午
 */
@Data
@ConfigurationProperties(prefix = "winter.data.redis")
public class RedisConfig {
    private Map<String, RedisProperties> template;
}
