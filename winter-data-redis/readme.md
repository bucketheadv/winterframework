## 用法

```yaml
winter:
  data:
    redis:
      enabled: true
      primary: risk
      template:
        user:
          master:
            host: localhost
            port: 6379
            db: 0
          slaves:
            - host: localhost
              port: 6379
              db: 1
        risk:
          master:
            host: localhost
            port: 6379
            db: 5
```

```java
@Service
class YourService {
    @Resource("userRedisTemplate")
    private RedisTemplate userRedisTemplate;

    @Resource("riskRedisTemplate")
    private RedisTemplate riskRedisTemplate;
}
```