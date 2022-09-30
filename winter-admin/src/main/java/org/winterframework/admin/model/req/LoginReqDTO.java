package org.winterframework.admin.model.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:54 PM
 */
@Data
public class LoginReqDTO {
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
