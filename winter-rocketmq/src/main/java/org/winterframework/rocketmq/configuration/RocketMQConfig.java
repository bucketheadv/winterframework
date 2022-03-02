package org.winterframework.rocketmq.configuration;

import lombok.Data;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;

import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/3/2 12:56 下午
 */
@Data
public class RocketMQConfig {
    private String primary;

    private Map<String, RocketMQProperties> template;
}
