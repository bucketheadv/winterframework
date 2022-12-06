## USAGE

```java
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.winterframework.cache.annotation.MultiCacheEvict;
import org.winterframework.cache.annotation.MultiCacheable;

import java.util.List;

@Service
public class UserDaoService {
	@Resource
	private UserMapper userMapper;

	@Cacheable(value = "user", key = "#id")
	public UserEntity getById(Long id) {
		return userMapper.getById(id);
	}

	@CacheEvict(value = "user", key = "#id")
	public int deleteById(Long id) {
		return userMapper.deleteById(id);
	}

	@MultiCacheable(prefix = "user", key = "#ids")
	public List<UserEntity> listByIds(List<Long> ids) {
		return userMapper.listByIds(ids);
	}

	@MultiCacheEvict(prefix = "user", key = "#ids")
	public int deleteByIds(List<Long> ids) {
		return userMapper.deleteByIds(ids);
	}
}
```

If your need define your key generator,

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.cache.generator.WinterCacheKeyGenerator;

import java.lang.reflect.Method;

@Configuration
public class MyConfiguration {
	@Bean
	public WinterCacheKeyGenerator winterCacheKeyGenerator() {
		return new MyGenerator();
	}

	public static class MyGenerator extends WinterCacheKeyGenerator {
		@Override
		public String invoke(Method method, Object... params) {
			// override your code here
//			return super.invoke(method, params);
		}
	}
}
```