package org.winterframework.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sven
 * Created on 2023/6/10 8:42 PM
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "winter.security")
public class WinterSecurityProperties {
    /**
     * 登录地址
     */
    private String loginUrl = "/login";

    /**
     * 登出地址
     */
    private String logoutUrl = "/logout";

    /**
     * 不鉴权的地址列表
     */
    private String[] whitelistUrls = new String[0];

    /**
     * 设置鉴权用户
     */
    private User user;

    @Getter
    @Setter
    public static class User {
        private String name;

        private String password;
    }
}
