package org.winterframework.tio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.utils.jfinal.P;

@SpringBootApplication
public class WinterStarterTioServerApplication {

    public static void main(String[] args) {
        P.use("app.properties");
        SpringApplication.run(WinterStarterTioServerApplication.class, args);
    }

}
