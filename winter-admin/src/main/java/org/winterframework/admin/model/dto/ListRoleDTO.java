package org.winterframework.admin.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/10/10 8:41 AM
 */
@Getter
@Setter
public class ListRoleDTO {
	private String roleName;

	private Boolean isSuperAdmin;

	private Integer pageNum = 1;

	private Integer pageSize = 20;
}
