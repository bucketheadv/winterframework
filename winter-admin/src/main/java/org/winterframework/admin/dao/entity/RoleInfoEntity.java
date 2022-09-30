package org.winterframework.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 3:09 PM
 */
@Data
@TableName("role_info")
public class RoleInfoEntity {
	@TableId(type = IdType.AUTO)
	private Long id;

	private String roleName;

	private Boolean isSuperAdmin;

	private Date createTime;

	private Date updateTime;
}
