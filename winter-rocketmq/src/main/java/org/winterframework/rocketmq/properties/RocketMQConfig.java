package org.winterframework.rocketmq.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.rocketmq.constants.Const;

import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/3/2 12:56 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = Const.configPrefix)
public class RocketMQConfig {
    /**
     * 是否启用winter rocketMQ
     */
    private boolean enabled = false;
    /**
     * 主配置名称
     */
    private String primary;

    private Map<String, RocketMQProperties> template;
}
