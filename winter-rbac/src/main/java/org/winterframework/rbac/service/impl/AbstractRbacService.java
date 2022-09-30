package org.winterframework.rbac.service.impl;

import org.winterframework.core.tool.CollectionTool;
import org.winterframework.rbac.model.Permission;
import org.winterframework.rbac.model.Role;
import org.winterframework.rbac.model.User;
import org.winterframework.rbac.service.RbacService;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:12 AM
 */
public abstract class AbstractRbacService implements RbacService {
	@Override
	public boolean hasUserPermForUrl(Serializable userId, String url) {
		User user = getUser(userId);
		if (user == null) {
			return false;
		}
		if (CollectionTool.isEmpty(user.getRoles())) {
			return false;
		}

		for (Role role : user.getRoles()) {
			if (hasRolePermForUrl(role, url)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasRolePermForUrl(Role role, String url) {
		if (role == null || CollectionTool.isEmpty(role.getPermissions())) {
			return false;
		}
		for (Permission permission : role.getPermissions()) {
			if (Objects.equals(permission.getUrl(), url)) {
				return true;
			}
		}
		return false;
	}

	public abstract User getUser(Serializable id);
}
