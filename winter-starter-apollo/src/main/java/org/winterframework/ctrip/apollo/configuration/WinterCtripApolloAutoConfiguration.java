package org.winterframework.ctrip.apollo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:00 下午
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.winterframework.ctrip.apollo")
public class WinterCtripApolloAutoConfiguration {
}
