package org.winterframework.job.properties;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/3/8 4:00 下午
 */
@Data
public class JobProperties {
    private String adminAddresses;
    private String accessToken;
    private String appname;
    private String address;
    private String ip;
    private int port = 9999;
    private String logPath = System.getProperty("user.home") + "/logs/task-center/jobhandler";
    private int logRetentionDays = 2;
}
