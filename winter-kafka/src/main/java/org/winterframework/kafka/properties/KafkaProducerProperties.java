package org.winterframework.kafka.properties;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * Created on 2022/2/25 11:34 下午
 */
@Data
public class KafkaProducerProperties {
    private String bootstrapServers = "localhost:9092";

    private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";

    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";

    private Map<String, Object> extra = new HashMap<>();
}
