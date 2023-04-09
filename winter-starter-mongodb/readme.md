## USAGE

```yaml
winter:
  data:
    mongodb:
      enabled: true
      template:
        main:
          host: localhost:27017
        second:
          host: localhost:27018
```

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoService {
    @Autowired
    @Qualifier("mainMongoTemplate")
    private MongoTemplate mainMongoTemplate;
    
    @Autowired
    @Qualifier("secondMongoTemplate")
    private MongoTemplate secondMongoTemplate;
}
```