package org.winterframework.jwt.session;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:02 PM
 */
@Getter
@Setter
public class UserToken {
	private Long uid;

	private String username;

	private Long expireAt;
}
