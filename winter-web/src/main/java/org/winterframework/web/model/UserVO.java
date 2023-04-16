package org.winterframework.web.model;

import lombok.Getter;
import lombok.Setter;
import org.winterframework.core.sensitive.annotation.Sensitive;
import org.winterframework.core.sensitive.strategy.SensitiveStrategy;

@Getter
@Setter
public class UserVO {
    private Long id;

    @Sensitive(strategy = SensitiveStrategy.USERNAME)
    private String username;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String mobile;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;

    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
    private String address;
}
