package org.winterframework.admin.model.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 3:46 PM
 */
@Data
public class UpdateRoleReqDTO {
	private Long id;

	@NotBlank
	private String roleName;

	private Boolean isSuperAdmin;

	private List<Long> permissionIds;
}
