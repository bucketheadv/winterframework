## USAGE

```java
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@Table(name = "user")
public class UserEntity {
	@Id
	private Long id;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 结束时间
	 */
	private Date updateTime;
}
```

```java
import org.apache.ibatis.annotations.Mapper;
import org.winterframework.tk.mybatis.mapper.base.BaseTkMapper;

@Mapper
public interface UserMapper extends BaseTkMapper<UserEntity, Long> {
}
```

```java
import org.winterframework.tk.mybatis.service.base.BaseTkService;

public interface UserService extends BaseTkService<UserEntity, Long> {
	UserEntity getByEmail(String email);
}
```

```java
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public UserEntity getByEmail(String email) {
		UserEntity user = new UserEntity();
		user.setEmail("test@aa.com");
		return baseMapper.selectOne(user);
    }
}
```