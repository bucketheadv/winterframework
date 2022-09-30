package org.winterframework.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winterframework.core.api.Errorable;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:41 PM
 */
@Getter
@AllArgsConstructor
public enum BizErrorCode implements Errorable {
	EMAIL_OR_PASSWORD_INVALID(300001, "error.email_or_password_invalid"),
	;
	private final int code;

	private final String i18nCode;
}
