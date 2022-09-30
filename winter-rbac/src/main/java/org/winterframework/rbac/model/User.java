package org.winterframework.rbac.model;

import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:11 AM
 */
@Data
public class User {
	private List<Role> roles;
}
