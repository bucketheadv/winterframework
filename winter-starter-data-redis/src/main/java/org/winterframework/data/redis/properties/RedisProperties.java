package org.winterframework.data.redis.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sven
 * Created on 2022/1/14 10:28 下午
 */
@Getter
@Setter
public class RedisProperties {
    /**
     * host
     */
    private String host;
    /**
     * 端口
     */
    private int port = 6379;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库
     */
    private int db = 0;

    /**
     * 线程池最大线程数
     */
    private int maxTotal = 8;
    /**
     * 线程池最大闲置线程数
     */
    private int maxIdle = 8;
    /**
     * 线程池最小闲置线程数
     */
    private int minIdle = 0;

    /**
     * 是否在创建时测试该连接是否还可用
     */
    private boolean testOnCreate = true;
    /**
     * 是否在借出线程时测试该连接是否还可用
     */
    private boolean testOnBorrow = true;
    /**
     * 是否在线程池回收线程时测试该连接是否还可用
     */
    private boolean testOnReturn = false;
    /**
     * 是否在闲置时测试该连接是否还可用
     */
    private boolean testWhileIdle = true;

    /**
     * 是否启用jmx
     */
    private boolean jmxEnabled;
    /**
     * jmx名称前缀
     */
    private String jmxNamePrefix;
    /**
     * jmx基本名称
     */
    private String jmxNameBase;
}
