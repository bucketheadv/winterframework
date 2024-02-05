package org.winterframework.etcd.configuration;

import io.etcd.jetcd.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winterframework.etcd.props.EtcdProperties;

/**
 * @author qinglin.liu
 * created at 2024/2/5 20:14
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.etcd")
public class WinterEtcdConfiguration {
    @Bean
    public Client client(EtcdProperties properties) {
        return Client.builder()
                .endpoints(properties.getEndpoints())
                .build();
    }
}
