package org.winterframework.admin.dao.service;

import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.tk.mybatis.service.TkService;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:07 PM
 */
public interface UserInfoDaoService extends TkService<UserInfoEntity, Long> {
	/**
	 * 根据邮箱地址获取用户信息
	 * @param email 邮箱地址
	 * @return UserInfoEntity
	 */
	UserInfoEntity getByEmail(String email);
}
