package org.winterframework.kafka.core;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * @author sven
 * Created on 2022/2/26 2:16 下午
 */
public class WinterDefaultKafkaConsumerFactory<K, V> extends ConcurrentKafkaListenerContainerFactory<K, V> {
    public WinterDefaultKafkaConsumerFactory(ConsumerFactory<K, V> consumerFactory, Long pollingTimeout) {
        super.setConsumerFactory(consumerFactory);
        getContainerProperties().setPollTimeout(pollingTimeout);
    }
}
