package org.winterframework.admin.model.req;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/10 8:41 AM
 */
@Data
public class QueryRoleReqDTO {
	private String roleName;

	private Boolean isSuperAdmin;

	private Integer pageNum = 1;

	private Integer pageSize = 20;
}
