package org.winterframework.admin.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:05 PM
 */
@Data
@Table(name = "user_info")
public class UserInfoEntity {
	@Id
	private Long id;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 结束时间
	 */
	private Date updateTime;
}
