package org.winterframework.data.redis.core;

import org.winterframework.data.redis.commands.JedisMultiCallback;
import org.winterframework.data.redis.commands.JedisPipelineCallback;
import redis.clients.jedis.commands.*;

import java.io.Closeable;
import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/2/11 10:00 上午
 */
public interface RedisTemplate extends ServerCommands, DatabaseCommands, JedisCommands, JedisBinaryCommands, ControlCommands, ControlBinaryCommands, ClusterCommands, ModuleCommands, GenericControlCommands, SentinelCommands, Closeable {
    /**
     * multi操作
     * @param callback 回调方法
     * @return java.util.List<Object>
     */
    List<Object> multi(JedisMultiCallback callback);

    /**
     * 管道操作
     * @param callback 回调方法
     * @return java.util.List<Object>
     */
    List<Object> doInMasterPipeline(JedisPipelineCallback callback);

    /**
     * 管道操作
     * @param callback 回调方法
     * @return java.util.List<Object>
     */
    List<Object> doInSlavePipeline(JedisPipelineCallback callback);
}
