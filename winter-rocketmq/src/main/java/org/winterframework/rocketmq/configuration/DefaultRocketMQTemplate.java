package org.winterframework.rocketmq.configuration;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;

/**
 * @author qinglinl
 * Created on 2022/3/2 1:28 下午
 */
public class DefaultRocketMQTemplate extends RocketMQTemplate {
    public DefaultRocketMQTemplate(DefaultMQProducer producer, DefaultLitePullConsumer consumer, RocketMQMessageConverter messageConverter) {
        super.setProducer(producer);
        super.setConsumer(consumer);
        super.setMessageConverter(messageConverter.getMessageConverter());
    }
}
