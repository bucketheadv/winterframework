package org.winterframework.kafka.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.winterframework.kafka.core.WinterDefaultKafkaConsumerFactory;
import org.winterframework.kafka.properties.KafkaConfig;
import org.winterframework.kafka.properties.KafkaConsumerProperties;
import org.winterframework.kafka.properties.KafkaProducerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * Created on 2022/2/25 10:26 下午
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
    private final KafkaConfig kafkaConfig;

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        initProducers(beanDefinitionRegistry);
        initConsumers(beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    private void initProducers(BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, KafkaProducerProperties> producers = kafkaConfig.getProducers();
        if (CollectionUtils.isEmpty(producers)) {
            return;
        }
        boolean isPrimary = true;
        for (String name : producers.keySet()) {
            KafkaProducerProperties properties = producers.get(name);
            Map<String, Object> kafkaConfigMap = new HashMap<>(properties.getExtra());
            kafkaConfigMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            kafkaConfigMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
            kafkaConfigMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());

            BeanDefinition producerFactoryBean = BeanDefinitionBuilder.rootBeanDefinition(DefaultKafkaProducerFactory.class)
                    .setPrimary(isPrimary)
                    .addConstructorArgValue(kafkaConfigMap)
                    .setDestroyMethodName("destroy")
                    .getBeanDefinition();
            String producerFactoryName = name + ProducerFactory.class.getSimpleName();
            beanDefinitionRegistry.registerBeanDefinition(producerFactoryName, producerFactoryBean);

            String key = name + KafkaTemplate.class.getSimpleName();
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(KafkaTemplate.class)
                    .setPrimary(isPrimary)
                    .addConstructorArgReference(producerFactoryName)
                    .setDestroyMethodName("destroy")
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(key, beanDefinition);
            log.info("注册KafkaTemplate [{}] 成功", key);
            if (isPrimary) {
                log.info("KafkaTemplate [{}] 被设置为 primary", key);
            }
            isPrimary = false;
        }
    }

    private void initConsumers(BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, KafkaConsumerProperties> consumers = kafkaConfig.getConsumers();
        if (CollectionUtils.isEmpty(consumers)) {
            return;
        }

        boolean isPrimary = true;
        for (String name : consumers.keySet()) {
            KafkaConsumerProperties properties = consumers.get(name);
            Map<String, Object> kafkaConfigMap = new HashMap<>(properties.getExtra());
            kafkaConfigMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
            kafkaConfigMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getKeyDeserializer());
            kafkaConfigMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getValueDeserializer());
            ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(kafkaConfigMap);

            BeanDefinition consumerFactoryBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultKafkaConsumerFactory.class)
                    .setPrimary(isPrimary)
                    .addConstructorArgValue(kafkaConfigMap)
                    .getBeanDefinition();
            String consumerFactoryBeanKey = name + "ConsumerFactory";
            beanDefinitionRegistry.registerBeanDefinition(consumerFactoryBeanKey, consumerFactoryBeanDefinition);

            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
            containerFactory.setConsumerFactory(consumerFactory);
            containerFactory.getContainerProperties().setPollTimeout(properties.getPollTimeout());
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(WinterDefaultKafkaConsumerFactory.class)
                    .setPrimary(isPrimary)
                    .addConstructorArgReference(consumerFactoryBeanKey)
                    .addConstructorArgValue(1000L)
                    .getBeanDefinition();
            String key = name + "KafkaContainerFactory";
            beanDefinitionRegistry.registerBeanDefinition(key, beanDefinition);
            log.info("注册KafkaContainerFactory [{}] 成功", key);
            if (isPrimary) {
                log.info("KafkaContainerFactory [{}] 被设置为 primary", key);
            }
            isPrimary = false;
        }
    }
}
