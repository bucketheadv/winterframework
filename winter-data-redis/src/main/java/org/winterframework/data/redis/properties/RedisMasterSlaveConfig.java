package org.winterframework.data.redis.properties;

import lombok.Data;

import java.util.List;

/**
 * @author sven
 * Created on 2022/2/28 11:13 下午
 */
@Data
public class RedisMasterSlaveConfig {
    private RedisProperties master;

    private List<RedisProperties> slaves;
}
