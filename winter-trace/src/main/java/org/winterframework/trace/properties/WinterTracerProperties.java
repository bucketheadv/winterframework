package org.winterframework.trace.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2023/3/24 9:26 PM
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "winter.tracer")
public class WinterTracerProperties {
    private String traceId = "X-TraceId";
}
