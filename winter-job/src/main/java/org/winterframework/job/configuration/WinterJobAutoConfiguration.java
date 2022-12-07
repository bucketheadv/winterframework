package org.winterframework.job.configuration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.winterframework.core.tool.StringTool;
import org.winterframework.job.constants.Const;
import org.winterframework.job.properties.JobProperties;

/**
 * @author qinglinl
 * Created on 2022/3/8 3:57 下午
 */
@Configuration
@ConditionalOnProperty(prefix = Const.configPrefix, value = "enabled", havingValue = "true")
public class WinterJobAutoConfiguration implements EnvironmentAware {
    private Environment environment;
    @Value("${spring.application.name:}")
    private String applicationName;

    @Bean
    public JobProperties jobProperties() {
        return Binder.get(environment).bind(Const.configPrefix, JobProperties.class).get();
    }

    @Bean
    @ConditionalOnMissingBean
    public XxlJobSpringExecutor xxlJobSpringExecutor(JobProperties jobProperties) {
        String appName = jobProperties.getAppName();
        appName = StringTool.defaultIfBlank(appName, applicationName);
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAppname(appName);
        executor.setAdminAddresses(jobProperties.getAdminAddresses());
        executor.setAccessToken(jobProperties.getAccessToken());
        executor.setAddress(jobProperties.getAddress());
        executor.setIp(jobProperties.getIp());
        executor.setPort(jobProperties.getPort());
        executor.setLogPath(jobProperties.getLogPath());
        executor.setLogRetentionDays(jobProperties.getLogRetentionDays());
        return executor;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
