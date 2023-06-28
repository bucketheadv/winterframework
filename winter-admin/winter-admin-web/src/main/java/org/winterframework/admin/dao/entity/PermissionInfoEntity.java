package org.winterframework.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Getter
@Setter
@Table(name = "permission_info")
public class PermissionInfoEntity {
	@Id
	private Long id;

	private String permissionName;

	private String uri;

	private Date createTime;

	private Date updateTime;
}
