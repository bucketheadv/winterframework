package org.winterframework.admin.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:56 PM
 */
@Data
public class DeletePermissionDTO {
	private List<Long> ids;
}
