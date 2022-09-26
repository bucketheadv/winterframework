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

	public ServiceException(int errorCode) {
		super("");
		this.code = errorCode;
	}

	public ServiceException(int errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public ServiceException(Errorable errorable) {
		this(errorable.getCode(), I18n.get(errorable.getMessage()));
	}

	public ServiceException(int errorCode, String message, Throwable clauses) {
		super(message, clauses);
		this.code = errorCode;
	}

	@Override
	public int getCode() {
		return code;
	}
}
