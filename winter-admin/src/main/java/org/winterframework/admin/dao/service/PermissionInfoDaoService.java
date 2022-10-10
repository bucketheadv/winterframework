package org.winterframework.admin.dao.service;

import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.tk.mybatis.service.TkService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:13 PM
 */
public interface PermissionInfoDaoService extends TkService<PermissionInfoEntity, Long> {
	List<RolePermissionEntity> getPermissionsByRoleId(Long roleId);

	/**
	 * 删除角色权限
	 * @param roleId 角色id
	 * @param exceptPermissionIds 不删除的权限id
	 */
	void deleteRolePermission(Long roleId, List<Long> exceptPermissionIds);

	/**
	 * 创建角色权限
	 * @param roleId 角色id
	 * @param permissionIds 权限id
	 */
	void createRolePermissions(Long roleId, List<Long> permissionIds);
}
