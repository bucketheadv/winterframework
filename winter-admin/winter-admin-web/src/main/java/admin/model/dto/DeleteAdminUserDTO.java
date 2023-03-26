package admin.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:51 AM
 */
@Getter
@Setter
public class DeleteAdminUserDTO {
	@NotEmpty
	private List<Long> ids;
}
