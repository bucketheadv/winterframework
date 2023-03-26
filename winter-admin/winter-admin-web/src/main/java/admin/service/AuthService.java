package admin.service;

import admin.dao.entity.AdminUserEntity;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:21 PM
 */
public interface AuthService {
	/**
	 * 通过邮箱获取用户
	 * @param email 邮箱地址
	 * @param password 密码
	 * @return UserInfoEntity
	 */
	AdminUserEntity loginByEmail(String email, String password);
}
