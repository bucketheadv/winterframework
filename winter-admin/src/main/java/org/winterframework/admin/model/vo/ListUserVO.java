package org.winterframework.admin.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:17 AM
 */
@Data
public class ListUserVO {
	private Long id;

	private String email;

	private Date createTime;

	private Date updateTime;
}
