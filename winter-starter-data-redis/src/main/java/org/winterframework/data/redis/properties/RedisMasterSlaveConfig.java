package org.winterframework.data.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.winterframework.data.redis.constants.LoadBalanceEnum;

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
     * 从库负载均衡算法
     * 轮询算法会先ping，ping不通时自动跳到下一节点
     */
    private LoadBalanceEnum loadBalance = LoadBalanceEnum.ROUND_ROBIN;

    /**
     * 从库配置
     */
    private List<RedisProperties> slaves;
}
