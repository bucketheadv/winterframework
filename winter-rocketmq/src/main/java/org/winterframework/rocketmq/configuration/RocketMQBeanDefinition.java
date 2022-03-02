package org.winterframework.rocketmq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.winterframework.core.tool.StringTool;
import org.winterframework.rocketmq.core.DefaultRocketMQTemplate;
import org.winterframework.rocketmq.properties.RocketMQConfig;

import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/3/2 1:06 下午
 */
@Slf4j
public class RocketMQBeanDefinition implements BeanDefinitionRegistryPostProcessor {
    private final RocketMQConfig rocketMQConfig;

    private final RocketMQMessageConverter messageConverter;

    public RocketMQBeanDefinition(RocketMQConfig rocketMQConfig, RocketMQMessageConverter messageConverter) {
        this.rocketMQConfig = rocketMQConfig;
        this.messageConverter = messageConverter;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Map<String, RocketMQProperties> configMap = rocketMQConfig.getTemplate();
        String primaryKey = configMap.containsKey(rocketMQConfig.getPrimary()) ? rocketMQConfig.getPrimary() : null;
        for (String key : configMap.keySet()) {
            if (StringTool.isBlank(primaryKey)) {
                primaryKey = key;
            }
            boolean isPrimary = key.equals(primaryKey);
            DefaultMQProducer producer = buildMQProducer(configMap.get(key));
            DefaultLitePullConsumer consumer;
            try {
                consumer = buildMQConsumer(configMap.get(key));
            } catch (MQClientException e) {
                throw new RuntimeException(e);
            }
            BeanDefinition rocketMQTemplateBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultRocketMQTemplate.class)
                    .setPrimary(isPrimary)
                    .addConstructorArgValue(producer)
                    .addConstructorArgValue(consumer)
                    .addConstructorArgValue(messageConverter)
                    .setDestroyMethodName("destroy")
                    .getBeanDefinition();

            String name = key + "RocketMQTemplate";
            beanDefinitionRegistry.registerBeanDefinition(name, rocketMQTemplateBeanDefinition);
            log.info("注册RocketMQ: [{}]成功", name);
            if (isPrimary) {
                log.info("RocketMQ: primary RocketMQTemplate被设置为[{}]", key);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    private DefaultMQProducer buildMQProducer(RocketMQProperties rocketMQProperties) {
        RocketMQProperties.Producer producerConfig = rocketMQProperties.getProducer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.producer.group] must not be null");
        String accessChannel = rocketMQProperties.getAccessChannel();
        String ak = rocketMQProperties.getProducer().getAccessKey();
        String sk = rocketMQProperties.getProducer().getSecretKey();
        boolean isEnableMsgTrace = rocketMQProperties.getProducer().isEnableMsgTrace();
        String customizedTraceTopic = rocketMQProperties.getProducer().getCustomizedTraceTopic();
        DefaultMQProducer producer = RocketMQUtil.createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);
        producer.setNamesrvAddr(nameServer);
        if (!StringUtils.isEmpty(accessChannel)) {
            producer.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }

        producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryNextServer());
        return producer;
    }

    private DefaultLitePullConsumer buildMQConsumer(RocketMQProperties rocketMQProperties) throws MQClientException {
        RocketMQProperties.Consumer consumerConfig = rocketMQProperties.getConsumer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = consumerConfig.getGroup();
        String topicName = consumerConfig.getTopic();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.consumer.group] must not be null");
        Assert.hasText(topicName, "[rocketmq.consumer.topic] must not be null");
        String accessChannel = rocketMQProperties.getAccessChannel();
        MessageModel messageModel = MessageModel.valueOf(consumerConfig.getMessageModel());
        SelectorType selectorType = SelectorType.valueOf(consumerConfig.getSelectorType());
        String selectorExpression = consumerConfig.getSelectorExpression();
        String ak = consumerConfig.getAccessKey();
        String sk = consumerConfig.getSecretKey();
        int pullBatchSize = consumerConfig.getPullBatchSize();
        DefaultLitePullConsumer litePullConsumer = RocketMQUtil.createDefaultLitePullConsumer(nameServer, accessChannel, groupName, topicName, messageModel, selectorType, selectorExpression, ak, sk, pullBatchSize);
        litePullConsumer.setEnableMsgTrace(consumerConfig.isEnableMsgTrace());
        litePullConsumer.setCustomizedTraceTopic(consumerConfig.getCustomizedTraceTopic());
        return litePullConsumer;
    }
}
