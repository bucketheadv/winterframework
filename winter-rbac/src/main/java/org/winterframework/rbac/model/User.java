package org.winterframework.rbac.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:11 AM
 */
@Data
public class User {
	/**
	 * 用户id
	 */
	private Serializable id;

	/**
	 * 权限列表
	 */
	private List<Role> roles;
}
