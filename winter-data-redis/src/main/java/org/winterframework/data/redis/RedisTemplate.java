package org.winterframework.data.redis;

import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author sven
 * Created on 2022/1/14 11:04 下午
 */
public class RedisTemplate<T, R> {
    private RedisCommands<T, R> redisCommands;

    public RedisTemplate(RedisCommands<T, R> redisCommands) {
        this.redisCommands = redisCommands;
    }

    public RedisCommands<T, R> oper() {
        return redisCommands;
    }
}
