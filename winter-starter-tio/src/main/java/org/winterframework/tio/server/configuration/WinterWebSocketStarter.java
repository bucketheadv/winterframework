package org.winterframework.tio.server.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author qinglin.liu
 * created at 2023/12/20 19:53
 */
@Slf4j
@Getter
@Component
public class WinterWebSocketStarter implements CommandLineRunner {

    @Autowired
    private WsServerStarter wsServerStarter;

    @Override
    public void run(String... args) throws Exception {
        wsServerStarter.start();
    }
}
