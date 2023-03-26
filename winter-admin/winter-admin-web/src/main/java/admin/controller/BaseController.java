package admin.controller;

import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.i18n.enums.ErrorCode;

/**
 * @author qinglinl
 * Created on 2022/9/30 1:49 PM
 */
public class BaseController {
	public <T> ApiResponse<T> build(I18nErrorCode i18nEnum, T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(i18nEnum.getCode());
		apiResponse.setMessage(I18n.get(i18nEnum.getI18nCode()));
		apiResponse.setTimestamp(System.currentTimeMillis());
		apiResponse.setData(data);
		return apiResponse;
	}

	public <T> ApiResponse<T> build(I18nErrorCode i18nEnum) {
		return build(i18nEnum, null);
	}

	public <T> ApiResponse<T> build(T data) {
		return build(ErrorCode.OK, data);
	}
}
