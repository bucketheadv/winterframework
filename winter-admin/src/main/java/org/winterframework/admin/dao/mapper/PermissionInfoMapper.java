package org.winterframework.admin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.tk.mybatis.mapper.BaseTkMapper;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:12 PM
 */
@Mapper
public interface PermissionInfoMapper extends BaseTkMapper<PermissionInfoEntity, Long> {
	/**
	 * 根据角色id获取所有权限
	 * @param roleId 角色id
	 * @return
	 */
	List<RolePermissionEntity> getPermissionsByRoleId(@Param("roleId") Long roleId);

	List<RolePermissionEntity> getPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);

	void deleteRolePermission(@Param("roleId") Long roleId, @Param("toDelPermissionIds") List<Long> toDelPermissionIds);

	void createRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
