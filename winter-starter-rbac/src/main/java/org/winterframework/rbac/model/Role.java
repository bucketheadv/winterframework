package org.winterframework.rbac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:07 AM
 */
@Getter
@Setter
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
