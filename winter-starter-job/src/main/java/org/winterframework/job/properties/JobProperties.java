package org.winterframework.job.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.job.constants.Const;

/**
 * @author qinglinl
 * Created on 2022/3/8 4:00 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = Const.configPrefix)
public class JobProperties {
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * job admin地址
     */
    private String adminAddresses;
    /**
     * 鉴权Token
     */
    private String accessToken;
    /**
     * app名称
     */
    private String appName;
    /**
     * 地址
     */
    private String address;
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private int port = 9999;
    /**
     * log地址
     */
    private String logPath = System.getProperty("user.home") + "/logs/task-center/jobhandler";
    /**
     * 日志保留天数
     */
    private int logRetentionDays = 2;
}
