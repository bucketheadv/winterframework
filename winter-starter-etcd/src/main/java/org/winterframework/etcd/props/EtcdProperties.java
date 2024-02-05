package org.winterframework.etcd.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinglin.liu
 * created at 2024/2/5 20:12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "winter.etcd")
public class EtcdProperties {
    /**
     * 节点列表，多个以英文逗号分隔
     */
    private String endpoints = "http://127.0.0.1:2379";
}
