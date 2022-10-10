package org.winterframework.jwt.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winterframework.core.api.I18nEnumerable;

/**
 * @author qinglinl
 * Created on 2022/9/27 2:08 PM
 */
@Getter
@AllArgsConstructor
public enum WebErrorCode implements I18nEnumerable {
	TOKEN_INVALID(200001, "error.token_invalid"),

	TOKEN_ENV_INCONSISTENT(200002, "error.token_env_inconsistent"),
	TOKEN_EXPIRED(200003, "error.token_expired"),

	SIGN_INVALID(200004, "error.sign_invalid"),
	;

	private final int code;

	private final String i18nCode;
}
