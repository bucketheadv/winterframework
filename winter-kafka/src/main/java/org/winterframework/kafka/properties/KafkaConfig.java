package org.winterframework.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.kafka.constants.Const;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/2/25 10:23 下午
 */
@Data
@ConfigurationProperties(prefix = Const.configPrefix)
public class KafkaConfig {
    /**
     * 是否启用winter kafka
     */
    private boolean enabled = false;

    /**
     * 消费者配置
     */
    private Map<String, KafkaConsumerProperties> consumers;

    /**
     * 生产者配置
     */
    private Map<String, KafkaProducerProperties> producers;
}
