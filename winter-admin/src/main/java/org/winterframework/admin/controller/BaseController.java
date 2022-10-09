package org.winterframework.admin.controller;

import org.winterframework.core.api.Errorable;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:49 PM
 */
public class BaseController {
	public <T> ApiResponse<T> build(Errorable errorable, T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(errorable.getCode());
		apiResponse.setMessage(I18n.get(errorable.getI18nCode()));
		apiResponse.setTimestamp(System.currentTimeMillis());
		apiResponse.setData(data);
		return apiResponse;
	}

	public <T> ApiResponse<T> build(Errorable errorable) {
		return build(errorable, null);
	}

	public <T> ApiResponse<T> build(T data) {
		return build(ErrorCode.OK, data);
	}
}
