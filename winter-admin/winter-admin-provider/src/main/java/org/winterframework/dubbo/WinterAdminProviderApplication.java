package org.winterframework.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sven
 * Created on 2023/3/26 9:31 PM
 */
@EnableDubbo
@SpringBootApplication
public class WinterAdminProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(WinterAdminProviderApplication.class, args);
    }
}
