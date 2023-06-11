```yaml
winter:
  security:
    login-url: /login # 配置登录地址
    logout-url: /logout # 配置登出地址
    whitelist-urls: # 配置跳过鉴权的地址
      - /login
    user: # 如果要使用固定配置用户登录
      name: user # 设置实际用户名
      password: 123 # 设置实际密码
```

# 不使用单独配置用户时，需要重写UserDetailsService接口，并声明为Bean

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author sven
 * Created on 2023/6/11 6:49 PM
 */
@Service
public class WinterUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 处理根据用户名查询用户的逻辑
        String password = "";
        return new User(username, passwordEncoder.encode(password), AuthorityUtils.createAuthorityList("ADMIN"));
    }
}

```