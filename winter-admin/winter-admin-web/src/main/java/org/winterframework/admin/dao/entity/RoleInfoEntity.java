package org.winterframework.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:09 PM
 */
@Getter
@Setter
@Table(name = "role_info")
public class RoleInfoEntity {
	@Id
	private Long id;

	private String roleName;

	private Boolean isSuperAdmin;

	private Date createTime;

	private Date updateTime;
}
