package org.winterframework.admin.model.dto;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:12 AM
 */
@Data
public class ListUserDTO {
	private Integer pageNum = 1;

	private Integer pageSize = 10;
}
