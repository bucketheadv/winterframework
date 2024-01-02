package org.winterframework.tio.server.configuration;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.core.stat.IpStatListener;
import org.tio.server.TioServerConfig;
import org.tio.utils.jfinal.P;
import org.tio.utils.time.Time;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.WsTioServerListener;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * @author qinglin.liu
 * created at 2023/12/26 21:35
 */
@Configuration
public class TioInitConfig {
    @Getter
    private static TioServerConfig tioServerConfig;

    @Autowired
    private IWsMsgHandler winterWsMsgHandler;
    @Autowired
    private WsTioServerListener wsTioServerListener;
    @Autowired
    private IpStatListener ipStatListener;

    @Bean
    public WsServerStarter wsServerStarter() throws Exception {
        String tioServerPort = System.getProperty("tio.server.port");
        int port = Integer.parseInt(StringUtils.defaultIfBlank(tioServerPort, "9091"));
        WsServerStarter wsServerStarter = new WsServerStarter(port, winterWsMsgHandler);

        tioServerConfig = wsServerStarter.getTioServerConfig();
        tioServerConfig.setName("http");
        tioServerConfig.setTioServerListener(wsTioServerListener);

        // 设置ip监控
        tioServerConfig.setIpStatListener(ipStatListener);
        // 设置ip统计时间段
        tioServerConfig.ipStats.addDurations(new Long[] { Time.MINUTE_1 * 5 });
        // 设置心跳超时时间
        tioServerConfig.setHeartbeatTimeout(1000 * 60);

        int useSSL = P.getInt("ws.use.ssl", 1);
        if (useSSL == 1) {
            String keyStoreFile = P.get("ssl.keystore", null);
            String trustStoreFile = P.get("ssl.truststore", null);
            String keyStorePwd = P.get("ssl.pwd", null);
            tioServerConfig.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
        }

        return wsServerStarter;
    }
}
