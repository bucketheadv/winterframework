package org.winterframework.data.redis.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qinglin.liu
 * created at 2024/3/15 10:06
 */
@Getter
@AllArgsConstructor
public enum LoadBalanceEnum {
    RANDOM("random", "随机算法"),
    ROUND_ROBIN("round_robin", "轮询算法"),
    ;
    private final String code;

    private final String desc;
}
