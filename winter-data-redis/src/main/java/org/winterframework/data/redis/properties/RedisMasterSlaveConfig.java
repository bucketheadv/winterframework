package org.winterframework.data.redis.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author sven
 * Created on 2022/2/28 11:13 下午
 */
@Getter
@Setter
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
