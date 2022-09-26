package org.winterframework.core.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winterframework.core.api.Errorable;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:35 PM
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements Errorable {
	OK(100000, "error.ok"),
	SYSTEM_ERROR(100001, "error.system_error"),
	METHOD_NOT_SUPPORT(100002, "error.method_not_support"),
	PARAM_ERROR(100003, "error.param_error"),
	PATH_NOT_FOUND(100004, "error.path_not_found"),
	;

	private final int code;

	private final String message;
}
