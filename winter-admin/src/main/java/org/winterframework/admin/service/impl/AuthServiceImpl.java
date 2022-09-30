package org.winterframework.admin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.winterframework.admin.dao.entity.UserInfoEntity;
import org.winterframework.admin.dao.service.UserInfoDaoService;
import org.winterframework.admin.enums.BizErrorCode;
import org.winterframework.admin.service.AuthService;
import org.winterframework.core.exception.ServiceException;

import java.util.Objects;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:25 PM
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserInfoDaoService userInfoDaoService;

	@Override
	public UserInfoEntity loginByEmail(String email, String password) {
		UserInfoEntity userInfoEntity = userInfoDaoService.getByEmail(email);
		if (userInfoEntity == null) {
			throw new ServiceException(BizErrorCode.EMAIL_OR_PASSWORD_INVALID);
		}
		if (!Objects.equals(userInfoEntity.getPassword(), password)) {
			throw new ServiceException(BizErrorCode.EMAIL_OR_PASSWORD_INVALID);
		}
		return userInfoEntity;
	}
}
