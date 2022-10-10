package org.winterframework.admin.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Data
@Table(name = "permission_info")
public class PermissionInfoEntity {
	@Id
	private Long id;

	private String permissionName;

	private String uri;

	private Date createTime;

	private Date updateTime;
}
