package org.winterframework.data.redis.commands;

import redis.clients.jedis.Pipeline;

/**
 * @author qinglinl
 * Created on 2022/1/26 3:30 下午
 */
@FunctionalInterface
public interface JedisPipelineCallback {
    void apply(Pipeline pipeline);
}
