package org.winterframework.rbac.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winterframework.core.api.I18nEnumerable;

/**
 * @author qinglinl
 * Created on 2022/9/30 10:34 AM
 */
@Getter
@AllArgsConstructor
public enum RbacErrorCode implements I18nEnumerable {
	PERMISSION_DENY(300001, "error.permission_deny"),
	;

	private final int code;

	private final String i18nCode;
}
