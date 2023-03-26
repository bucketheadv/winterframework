package admin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:48 PM
 */
@EnableDubbo
@SpringBootApplication
public class WinterAdminWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WinterAdminWebApplication.class, args);
	}
}
