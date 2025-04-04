package org.winterframework.admin.advice;

import org.winterframework.admin.enums.BizErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiData;
import org.winterframework.rbac.exception.RbacPermDeniedException;

/**
 * @author qinglinl
 * Created on 2022/11/4 6:18 PM
 */
@Slf4j
@RestControllerAdvice
public class AdminControllerAdvice {
	@ExceptionHandler(RbacPermDeniedException.class)
	public <T> ApiData<T> onRbacPermDeniedException(RbacPermDeniedException e) {
		log.error("Permission Denied", e);
		return buildResponse(BizErrorCode.USER_HAS_NO_RBAC_PERM);
	}

	private <T> ApiData<T> buildResponse(I18nErrorCode enumerable) {
		ApiData<T> response = new ApiData<>();
		response.setCode(enumerable.getCode());
		response.setMessage(I18n.get(enumerable.getI18nCode()));
		response.setTimestamp(System.currentTimeMillis());
		return response;
	}
}
