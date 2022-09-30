package org.winterframework.rbac.service;

import org.winterframework.rbac.model.Role;

import java.io.Serializable;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:07 AM
 */
public interface RbacService {
	/**
	 * 用户是否拥有权限
	 * @param userId 用户id
	 * @param url url地址
	 * @return boolean
	 */
	boolean hasUserPermForUrl(Serializable userId, String url);

	/**
	 * 角色是否有权限
	 * @param role 角色
	 * @param url url
	 * @return boolean
	 */
	boolean hasRolePermForUrl(Role role, String url);
}
