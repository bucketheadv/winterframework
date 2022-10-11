package org.winterframework.admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:54 PM
 */
@Data
public class LoginDTO {
	@NotBlank
	private String name;

	@NotBlank
	private String password;
}
