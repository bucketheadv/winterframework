package org.winterframework.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:05 PM
 */
@Data
@TableName("user_info")
public class UserInfoEntity {
	@TableId(type = IdType.AUTO)
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
