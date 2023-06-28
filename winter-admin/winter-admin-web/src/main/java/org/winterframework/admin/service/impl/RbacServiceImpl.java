package org.winterframework.admin.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.dao.entity.RolePermissionEntity;
import org.winterframework.admin.dao.entity.UserRoleEntity;
import org.winterframework.admin.dao.service.PermissionInfoDaoService;
import org.winterframework.admin.dao.service.RoleInfoDaoService;
import org.winterframework.admin.dao.service.AdminUserInfoDaoService;
import org.winterframework.rbac.model.Permission;
import org.winterframework.rbac.model.Role;
import org.winterframework.rbac.model.User;
import org.winterframework.rbac.service.impl.AbstractRbacService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:21 PM
 */
@Service
@AllArgsConstructor
public class RbacServiceImpl extends AbstractRbacService {
	private AdminUserInfoDaoService adminUserInfoDaoService;

	private RoleInfoDaoService roleInfoDaoService;

	private PermissionInfoDaoService permissionInfoDaoService;

	private final Cache<Serializable, User> caffeineCache = Caffeine.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.maximumSize(10_000)
			.build();

	@Override
	public User getUser(Serializable id) {
		return caffeineCache.get(id, this::getUserById);
	}

	private User getUserById(Serializable id) {
		AdminUserEntity adminUserEntity = adminUserInfoDaoService.selectByPrimaryKey((Long) id);
		if (adminUserEntity == null) {
			return null;
		}
		List<UserRoleEntity> userRoles = roleInfoDaoService.getUserRoleByUserId(adminUserEntity.getId());
		List<Role> roles = new ArrayList<>();
		for (UserRoleEntity userRole : userRoles) {
			Role role = new Role();
			role.setSuperAdmin(userRole.getIsSuperAdmin());
			List<RolePermissionEntity> rolePermissions = permissionInfoDaoService.getPermissionsByRoleId(userRole.getRoleId());
			List<Permission> permissions = new ArrayList<>();
			for (RolePermissionEntity rolePermission : rolePermissions) {
				Permission permission = new Permission();
				permission.setUrl(rolePermission.getUri());
				permission.setName(rolePermission.getPermissionName());
				permissions.add(permission);
			}

			role.setPermissions(permissions);
			roles.add(role);
		}

		User user = new User();
		user.setId(adminUserEntity.getId());
		user.setRoles(roles);
		return user;
	}
}
