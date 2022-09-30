package org.winterframework.admin.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.winterframework.admin.dao.entity.UserInfoEntity;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:07 PM
 */
public interface UserInfoDaoService extends IService<UserInfoEntity> {
	/**
	 * 根据邮箱地址获取用户信息
	 * @param email 邮箱地址
	 * @return UserInfoEntity
	 */
	UserInfoEntity getByEmail(String email);
}
