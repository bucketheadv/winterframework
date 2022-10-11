package org.winterframework.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winterframework.core.api.I18nEnumerable;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:41 PM
 */
@Getter
@AllArgsConstructor
public enum BizErrorCode implements I18nEnumerable {
	EMAIL_OR_PASSWORD_INVALID(300001, "error.email_or_password_invalid"),
	PASSWORD_CANNOT_BE_BLANK(300002, "error.password_cannot_be_blank"),
	;
	private final int code;

	private final String i18nCode;
}
