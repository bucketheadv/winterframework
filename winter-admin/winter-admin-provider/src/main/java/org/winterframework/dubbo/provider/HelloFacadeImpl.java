package org.winterframework.dubbo.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.winterframework.dubbo.facade.HelloFacade;

/**
 * @author sven
 * Created on 2023/3/26 9:27 PM
 */
@Service
@DubboService
public class HelloFacadeImpl implements HelloFacade {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
