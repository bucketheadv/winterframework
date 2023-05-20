## USAGE

In application.yml,

```yaml
winter:
  datasource:
    dynamic:
      default:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/db_user
        username: root
        password: 
        mapper-locations:
          - classpath*:/mapper/user/*.xml
      order-master:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/db_order
        username: root
        password: 
        mapper-locations:
          - classpath*:/mapper/order/*.xml
      order-slave:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/db_order
        username: root
        password:
        mapper-locations:
          - classpath*:/mapper/order/*.xml
```

In your java configuration,

```java
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"your_mapper_package_path"}) // REQUIRED !!!
public class MyConfiguration {
}
```

In mybatis mapper,

```java
import org.winterframework.dynamic.datasource.annotation.DataSource;

@DataSource("order-master")
public interface UserMapper {

    /**
     * Will Use Slave DB
     */
    @DataSource("order-slave")
    UserEntity selectById(Long id);

    /**
     * Will Use Master DB
     */
    UserEntity insert(UserEntity userEntity);
}
```