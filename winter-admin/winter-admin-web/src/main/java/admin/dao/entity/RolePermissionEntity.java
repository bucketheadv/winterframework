package admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:27 PM
 */
@Getter
@Setter
public class RolePermissionEntity {
	private Long roleId;

	private Long permissionId;

	private String permissionName;

	private String uri;
}
