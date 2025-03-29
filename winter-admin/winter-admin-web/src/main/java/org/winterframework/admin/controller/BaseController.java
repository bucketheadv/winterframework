package org.winterframework.admin.controller;

import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiData;
import org.winterframework.core.i18n.enums.ErrorCode;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:49 PM
 */
public class BaseController {
	public <T> ApiData<T> build(I18nErrorCode i18nEnum, T data) {
		ApiData<T> apiData = new ApiData<>();
		apiData.setCode(i18nEnum.getCode());
		apiData.setMessage(I18n.get(i18nEnum.getI18nCode()));
		apiData.setTimestamp(System.currentTimeMillis());
		apiData.setData(data);
		return apiData;
	}

	public <T> ApiData<T> build(I18nErrorCode i18nEnum) {
		return build(i18nEnum, null);
	}

	public <T> ApiData<T> build(T data) {
		return build(ErrorCode.OK, data);
	}
}
