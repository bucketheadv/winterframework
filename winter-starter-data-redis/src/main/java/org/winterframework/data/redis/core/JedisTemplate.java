package org.winterframework.data.redis.core;

import org.winterframework.data.redis.commands.JedisMultiCallback;
import org.winterframework.data.redis.commands.JedisPipelineCallback;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.commands.*;

import java.io.Closeable;
import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/2/11 10:00 上午
 */
public interface JedisTemplate extends ServerCommands, DatabaseCommands, JedisCommands, JedisBinaryCommands, ControlCommands, ControlBinaryCommands, ClusterCommands, ModuleCommands, GenericControlCommands, SentinelCommands, Closeable {
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

    /**
     * 发布消息
     * @param channel 发布主题
     * @param message 发布消息内容
     * @return
     */
    long publish(final byte[] channel, final byte[] message);

    /**
     * 发布消息
     * @param channel 发布主题
     * @param message 发布消息内容
     * @return
     */
    long publish(final String channel, final String message);

    /**
     * 订阅消息
     * @param jedisPubSub 回调方法
     * @param channels 订阅消息主题
     */
    void subscribe(final JedisPubSub jedisPubSub, final String... channels);

    /**
     * 订阅消息
     * @param jedisPubSub 回调方法
     * @param channels 订阅消息主题
     */
    void subscribe(BinaryJedisPubSub jedisPubSub, final byte[]... channels);

    /**
     * 订阅消息
     * @param jedisPubSub 回调方法
     * @param patterns 消息主题模式匹配
     */
    void psubscribe(final JedisPubSub jedisPubSub, final String... patterns);

    /**
     * 订阅消息
     * @param jedisPubSub 回调方法
     * @param patterns 消息主题模式匹配
     */
    void psubscribe(BinaryJedisPubSub jedisPubSub, final byte[]... patterns);
}
