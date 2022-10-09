package org.winterframework.admin.model.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:27 PM
 */
@Data
public class UpdatePermissionReqDTO {
	private Long id;

	@NotBlank
	private String permissionName;

	@NotBlank
	private String uri;
}
