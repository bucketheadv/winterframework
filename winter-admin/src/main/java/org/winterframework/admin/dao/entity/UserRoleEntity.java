package org.winterframework.admin.dao.entity;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:18 PM
 */
@Data
public class UserRoleEntity {
	private Long roleId;

	private String roleName;

	private Long userId;

	private Boolean isSuperAdmin;
}
