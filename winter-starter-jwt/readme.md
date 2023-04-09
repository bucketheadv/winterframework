## USAGE

```yaml
winter:
  jwt:
    checkToken: true
    checkSignature: false
    allowTokenEmpty: false
    defaultLocale: english
    supportLocales:
      - english
      - chinese
    tokenWhiteUriArray:
      - ^/api/your-url.*
    signatureWhiteUriArray:
      - /**
    pathPatterns:
      - /**
    excludePathPatterns:
      - /webjars
```

If you need override the BaseInterceptor,

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.jwt.interceptor.BasedInterceptor;

@Configuration
public class MyConfiguration {
	@Bean
	public BasedInterceptor interceptor() {
		return new MyCustomInterceptor();
	}

	public class MyCustomInterceptor extends BasedInterceptor {
		// write your code here
	}
}
```