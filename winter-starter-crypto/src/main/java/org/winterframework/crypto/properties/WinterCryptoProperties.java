package org.winterframework.crypto.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sven
 * Created on 2023/3/12 5:44 PM
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "winter.crypto")
public class WinterCryptoProperties {
    /**
     * 密钥映射表，key 对应 @EncryptedField 的 key 值, value是密钥值
     */
    private Map<String, String> secretKeyMap = new LinkedHashMap<>();
}
