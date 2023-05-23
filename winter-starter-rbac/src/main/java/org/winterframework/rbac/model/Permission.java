package org.winterframework.rbac.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:08 AM
 */
@Getter
@Setter
public class Permission {
	/**
	 * 权限名
	 */
	private String name;

	/**
	 * 权限地址
	 */
	private String url;
}
