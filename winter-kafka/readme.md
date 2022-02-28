# USAGE

```yaml
winter:
  data:
    kafka:
      consumers:
        main:
          bootstrap-servers: localhost:9092
#          key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
#          value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
#          extra:
#            client.id: my-client-id
#            auto.commit.interval.ms: 1000
#            auto.offset.reset: earliest
        second:
          bootstrap-servers: 192.168.25.1:9092
      producers:
        main:
          bootstrap-servers: localhost:9092
#          key-serializer: org.apache.kafka.common.serialization.StringSerializer
#          value-serializer: org.apache.kafka.common.serialization.StringSerializer
#          extra:
#            batch.size: 500
#            acks: 1
#            linger.ms: 1
#            transactional-id: xxxx
        second:
          bootstrap-servers: 192.168.25.1:9092
```

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {
    @KafkaListener(topics = "my_topic_1", groupId = "my_group_1", containerFactory = "mainKafkaContainerFactory")
    public void onTopic1Msg(String msg) {
    }

    @KafkaListener(topics = "my_topic_2", groupId = "my_group_2", containerFactory = "secondKafkaContainerFactory")
    public void onTopic2Msg(String msg) {
    }
}

@Component
public class MyKafkaSender {
    @Autowired
    @Qualifier("mainKafkaTemplate")
    public KafkaTemplate<String, String> mainKafkaTemplate;
    
    @Autowired
    @Qualifier("secondKafkaTemplate")
    private KafkaTemplate<String, String> secondKafkaTemplate;
}
```