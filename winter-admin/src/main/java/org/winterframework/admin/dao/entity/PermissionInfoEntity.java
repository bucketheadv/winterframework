package org.winterframework.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:11 PM
 */
@Data
@TableName("permission_info")
public class PermissionInfoEntity {
	@TableId(type = IdType.AUTO)
	private Long id;

	private String permissionName;

	private String uri;

	private Date createTime;

	private Date updateTime;
}
