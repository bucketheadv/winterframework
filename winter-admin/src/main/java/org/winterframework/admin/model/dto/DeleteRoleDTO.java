package org.winterframework.admin.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:45 PM
 */
@Data
public class DeleteRoleDTO {
	@NotEmpty
	private List<Long> ids;
}
