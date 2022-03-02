package org.winterframework.data.redis.properties;

import lombok.Data;

/**
 * @author sven
 * Created on 2022/1/14 10:28 下午
 */
@Data
public class RedisProperties {
    private String host;
    private int port = 6379;
    private String password;
    private int db = 0;

    private int maxTotal = 8;
    private int maxIdle = 8;
    private int minIdle = 0;

    private boolean testOnCreate;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;

    private boolean jmxEnabled;
    private String jmxNamePrefix;
    private String jmxNameBase;
}
