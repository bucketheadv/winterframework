package org.winterframework.kafka.properties;

import lombok.Data;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/2/25 10:23 下午
 */
@Data
public class KafkaConfig {
    private Map<String, KafkaConsumerProperties> consumers;

    private Map<String, KafkaProducerProperties> producers;
}
