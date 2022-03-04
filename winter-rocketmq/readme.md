## USAGE

```yaml
winter:
  data:
    rocketmq:
      enabled: true
      primary: main
      template:
        main:
          name-server: localhost:9876
          access-channel: xxx
          producer:
            group: test_producer_group
            access-key: xxx
            secret-key: xxx
```
## Consumer
```java
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "test_topic", consumerGroup = "test_consumer_group", nameServer = "${winter.data.rocketmq.template.main.name-server}")
public class RocketMQConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}
```

## Producer

```java
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RocketProducer {
    @Autowired
    @Qualifier("mainRocketMQTemplate")
    private RocketMQTemplate rocketMQTemplate;
}
```