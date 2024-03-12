package org.winterframework.web;

import org.dromara.dynamictp.core.spring.EnableDynamicTp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicTp
@SpringBootApplication
public class WinterWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WinterWebApplication.class, args);
    }
}
