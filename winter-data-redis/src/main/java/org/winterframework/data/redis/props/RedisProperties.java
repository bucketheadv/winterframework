package org.winterframework.data.redis.props;

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
}
