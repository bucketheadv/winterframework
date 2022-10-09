package org.winterframework.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.winterframework.admin.dao.entity.PermissionInfoEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:12 PM
 */
@Mapper
public interface PermissionInfoMapper extends BaseMapper<PermissionInfoEntity> {
	/**
	 * 根据角色id获取所有权限
	 * @param roleId 角色id
	 * @return
	 */
	List<RolePermissionEntity> getPermissionsByRoleId(@Param("roleId") Long roleId);

	List<RolePermissionEntity> getPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);

	void deleteRolePermission(@Param("roleId") Long roleId, @Param("exceptPermissionIds") List<Long> exceptPermissionIds);

	void createRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
