## USAGE

```yaml
winter:
  data:
    elasticsearch:
      enabled: true
      template:
        main:
          uris:
            - http://localhost:9200
```

```java
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	@Resource("mainElasticsearchTemplate")
	private ElasticsearchTemplate elasticsearchTemplate;
}
```