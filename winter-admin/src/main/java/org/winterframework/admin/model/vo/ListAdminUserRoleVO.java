package org.winterframework.admin.model.vo;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/11 10:20 AM
 */
@Data
public class ListAdminUserRoleVO {
	private Long roleId;

	private String roleName;

	private Boolean hasRole;
}
