package org.winterframework.core.exception;

import org.winterframework.core.api.Errorable;
import org.winterframework.core.i18n.I18n;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:27 PM
 */
public class ServiceException extends RuntimeException implements Errorable {
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

	public ServiceException(Errorable errorable) {
		this(errorable.getCode(), errorable.getI18nCode());
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
