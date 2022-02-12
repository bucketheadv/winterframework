## Install

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.bucketheadv</groupId>
    <artifactId>winterframework</artifactId>
    <version>v0.2.3</version>
</dependency>
```

## Usage

```properties
winter.data.redis.enabled=true
winter.data.redis.primary=risk
winter.data.redis.template.user.host=localhost
winter.data.redis.template.user.port=6379

winter.data.redis.template.risk.host=localhost
winter.data.redis.template.risk.port=6379
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