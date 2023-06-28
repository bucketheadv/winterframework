package org.winterframework.admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 3:46 PM
 */
@Getter
@Setter
public class UpdateRoleDTO {
	private Long id;

	@NotBlank
	private String roleName;

	private Boolean isSuperAdmin;

	private List<Long> permissionIds;
}
