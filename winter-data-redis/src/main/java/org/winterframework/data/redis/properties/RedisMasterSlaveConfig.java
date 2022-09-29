package org.winterframework.data.redis.properties;

import lombok.Data;

import java.util.List;

/**
 * @author sven
 * Created on 2022/2/28 11:13 下午
 */
@Data
public class RedisMasterSlaveConfig {
    /**
     * 主库配置
     */
    private RedisProperties master;

    /**
     * 从库配置
     */
    private List<RedisProperties> slaves;
}
