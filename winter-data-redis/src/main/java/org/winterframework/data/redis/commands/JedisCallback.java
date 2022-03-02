package org.winterframework.data.redis.commands;

import redis.clients.jedis.Jedis;

/**
 * @author qinglinl
 * Created on 2022/1/23 12:13 下午
 */
public interface JedisCallback<T> {
    T apply(Jedis jedis);
}
