package org.winterframework.data.redis.function;

import redis.clients.jedis.Transaction;

/**
 * @author qinglinl
 * Created on 2022/1/26 3:24 下午
 */
@FunctionalInterface
public interface JedisMultiCallback {
    void apply(Transaction transaction);
}
