package org.winterframework.admin.model.dto;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:09 AM
 */
@Data
public class ListPermissionDTO {
	private String permissionName;

	private String uri;

	private Integer pageNum = 1;

	private Integer pageSize = 20;
}
