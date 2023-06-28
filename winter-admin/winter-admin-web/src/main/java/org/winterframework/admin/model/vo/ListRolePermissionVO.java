package org.winterframework.admin.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/10/8 3:14 PM
 */
@Getter
@Setter
public class ListRolePermissionVO {
	private Long permissionId;

	private String permissionName;

	private String uri;

	private Boolean hasPerm;
}
