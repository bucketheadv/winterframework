package org.winterframework.admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:55 AM
 */
@Data
public class UpdateAdminUserDTO {
	private Long id;

	@NotBlank
	private String email;

	private String password;

	private List<Long> roleIds;
}
