package org.winterframework.admin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.AdminUserEntity;
import org.winterframework.admin.dao.service.AdminUserInfoDaoService;
import org.winterframework.admin.enums.BizErrorCode;
import org.winterframework.admin.service.AuthService;
import org.winterframework.core.i18n.exception.ServiceException;

import java.util.Objects;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:25 PM
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	private AdminUserInfoDaoService adminUserInfoDaoService;

	@Override
	public AdminUserEntity loginByEmail(String email, String password) {
		AdminUserEntity adminUserEntity = adminUserInfoDaoService.getByEmail(email);
		if (adminUserEntity == null) {
			throw new ServiceException(BizErrorCode.EMAIL_OR_PASSWORD_INVALID);
		}
		if (!Objects.equals(adminUserEntity.getPassword(), password)) {
			throw new ServiceException(BizErrorCode.EMAIL_OR_PASSWORD_INVALID);
		}
		return adminUserEntity;
	}
}
