package org.winterframework.data.redis.props;

import lombok.Data;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/1/14 10:35 下午
 */
@Data
public class RedisConfig {
    private String primary;

    private Map<String, RedisProperties> template;
}
