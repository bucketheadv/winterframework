package org.winterframework.admin.model.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:45 PM
 */
@Data
public class DeleteRoleReqDTO {
	@NotEmpty
	private List<Long> ids;
}
