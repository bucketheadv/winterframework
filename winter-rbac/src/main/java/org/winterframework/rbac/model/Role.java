package org.winterframework.rbac.model;

import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:07 AM
 */
@Data
public class Role {
	/**
	 * 是否是超级管理员
	 */
	private boolean isSuperAdmin;
	/**
	 * 权限列表
	 */
	private List<Permission> permissions;
}
