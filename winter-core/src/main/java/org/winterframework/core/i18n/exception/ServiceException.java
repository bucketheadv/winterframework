package org.winterframework.core.i18n.exception;

import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.I18n;

import java.io.Serial;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:27 PM
 */
public class ServiceException extends RuntimeException implements I18nErrorCode {
	@Serial
	private static final long serialVersionUID = 6426831533728128447L;

	private final int code;

	private final String i18nCode;

	public ServiceException(int errorCode) {
		super("");
		this.code = errorCode;
		this.i18nCode = "";
	}

	public ServiceException(int errorCode, String i18nCode) {
		super(I18n.get(i18nCode));
		this.code = errorCode;
		this.i18nCode = i18nCode;
	}

	public ServiceException(I18nErrorCode i18nErrorCode) {
		this(i18nErrorCode.getCode(), i18nErrorCode.getI18nCode());
	}

	public ServiceException(int errorCode, String i18nCode, Throwable clauses) {
		super(I18n.get(i18nCode), clauses);
		this.code = errorCode;
		this.i18nCode = i18nCode;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getI18nCode() {
		return i18nCode;
	}
}
