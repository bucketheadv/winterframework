package org.winterframework.admin.dao.entity;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:27 PM
 */
@Data
public class RolePermissionEntity {
	private Long roleId;

	private Long permissionId;

	private String permissionName;

	private String uri;
}
