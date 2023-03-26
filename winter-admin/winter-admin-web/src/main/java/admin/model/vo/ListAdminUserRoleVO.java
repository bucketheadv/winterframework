package admin.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/10/11 10:20 AM
 */
@Getter
@Setter
public class ListAdminUserRoleVO {
	private Long roleId;

	private String roleName;

	private Boolean hasRole;
}
