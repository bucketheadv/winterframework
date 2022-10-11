package org.winterframework.admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:27 PM
 */
@Data
public class UpdatePermissionDTO {
	private Long id;

	@NotBlank
	private String permissionName;

	@NotBlank
	private String uri;
}
