package org.winterframework.admin.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.winterframework.admin.enums.BizErrorCode;
import org.winterframework.core.api.I18nEnumerable;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.rbac.exception.RbacPermDeniedException;

/**
 * @author qinglinl
 * Created on 2022/11/4 6:18 PM
 */
@Slf4j
@RestControllerAdvice
public class AdminControllerAdvice {
	@ExceptionHandler(RbacPermDeniedException.class)
	public ApiResponse<?> onRbacPermDeniedException(RbacPermDeniedException e) {
		log.error("", e);
		return buildResponse(BizErrorCode.USER_HAS_NO_RBAC_PERM);
	}

	private ApiResponse<?> buildResponse(I18nEnumerable enumerable) {
		ApiResponse<?> response = new ApiResponse<>();
		response.setCode(enumerable.getCode());
		response.setMessage(I18n.get(enumerable.getI18nCode()));
		response.setTimestamp(System.currentTimeMillis());
		return response;
	}
}
