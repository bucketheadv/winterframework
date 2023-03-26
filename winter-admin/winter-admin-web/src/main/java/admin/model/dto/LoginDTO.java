package admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:54 PM
 */
@Getter
@Setter
public class LoginDTO {
	@NotBlank
	private String name;

	@NotBlank
	private String password;
}
