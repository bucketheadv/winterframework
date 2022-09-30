package org.winterframework.admin.model.res;

import lombok.Data;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:57 PM
 */
@Data
public class UserDetailResDTO {
	private Long id;

	private String email;

	private Date createTime;

	private Date updateTime;
}
