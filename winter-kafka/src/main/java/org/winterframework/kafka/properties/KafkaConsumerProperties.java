package org.winterframework.kafka.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * Created on 2022/2/25 10:36 下午
 */
@Getter
@Setter
public class KafkaConsumerProperties {
    private String bootstrapServers = "localhost:9092";

    private String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

    private String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

    private Long pollTimeout = 1000L;

    private Map<String, Object> extra = new HashMap<>();
}
