package org.winterframework.admin.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:09 AM
 */
@Getter
@Setter
public class ListPermissionDTO {
	private String permissionName;

	private String uri;

	private Integer pageNum = 1;

	private Integer pageSize = 20;
}
