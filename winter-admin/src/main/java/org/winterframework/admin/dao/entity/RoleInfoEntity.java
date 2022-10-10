package org.winterframework.admin.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:09 PM
 */
@Data
@Table(name = "role_info")
public class RoleInfoEntity {
	@Id
	private Long id;

	private String roleName;

	private Boolean isSuperAdmin;

	private Date createTime;

	private Date updateTime;
}
