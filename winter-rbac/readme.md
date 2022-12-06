## USAGE

```java
import org.springframework.stereotype.Service;
import org.winterframework.rbac.model.User;
import org.winterframework.rbac.service.impl.AbstractRbacService;

import java.io.Serializable;

@Service
public class MyService extends AbstractRbacService {
	@Override
	public User getUser(Serializable id) {
		// override your method here
		return null;
	}
}
```

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winterframework.rbac.configuration.aop.annotation.RbacPerm;

@RestController
public class MyController {
	@RbacPerm
	@RequestMapping("/test")
	public Object test() {
		return "OK";
	}
}
```