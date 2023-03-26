package admin.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:45 PM
 */
@Getter
@Setter
public class DeleteRoleDTO {
	@NotEmpty
	private List<Long> ids;
}
