package org.winterframework.tk.mybatis.configuration;

import com.github.pagehelper.PageInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:53 PM
 */
@Configuration
@ComponentScan(basePackages = "org.winterframework.tk.mybatis")
public class TkMybatisAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
