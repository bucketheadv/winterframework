package org.winterframework.admin.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.winterframework.rbac.model.Permission;
import org.winterframework.rbac.model.Role;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/10/8 11:33 AM
 */
@Getter
@Setter
public class LoginVO {
	private User user;

	private List<Role> roles;

	private List<Permission> permissions;

	private String token;

	private Date expireAt;

	@Getter
	@Setter
	public static class User {
		private String address;

		private String avatar;

		private String name;

		private Map<String, String> position;
	}
}
