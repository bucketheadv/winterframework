package org.winterframework.crypto.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2023/3/12 5:44 PM
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "winter.crypto")
public class WinterCryptoProperties {
    private String secretKey = "";
}
