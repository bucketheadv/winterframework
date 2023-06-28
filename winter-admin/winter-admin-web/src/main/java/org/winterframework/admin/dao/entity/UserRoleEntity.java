package org.winterframework.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:18 PM
 */
@Getter
@Setter
public class UserRoleEntity {
	private Long roleId;

	private String roleName;

	private Long adminUserId;

	private Boolean isSuperAdmin;
}
