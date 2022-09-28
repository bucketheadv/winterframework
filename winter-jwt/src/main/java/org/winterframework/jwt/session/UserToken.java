package org.winterframework.jwt.session;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:02 PM
 */
@Data
public class UserToken {
	private Long uid;

	private String username;

	private Long expireAt;
}
