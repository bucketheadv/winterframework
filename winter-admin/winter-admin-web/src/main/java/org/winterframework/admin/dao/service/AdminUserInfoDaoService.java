package org.winterframework.admin.dao.service;

import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.tk.mybatis.service.base.BaseTkService;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:07 PM
 */
public interface AdminUserInfoDaoService extends BaseTkService<AdminUserEntity, Long> {
	/**
	 * 根据邮箱地址获取用户信息
	 * @param email 邮箱地址
	 * @return UserInfoEntity
	 */
	AdminUserEntity getByEmail(String email);
}
