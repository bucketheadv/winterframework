package org.winterframework.web.configuration.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.enums.ErrorCode;
import org.winterframework.core.i18n.exception.ServiceException;
import org.winterframework.core.support.ApiResponse;

import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:41 PM
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(prefix = "winter.core.advice", value = "enabled", havingValue = "true", matchIfMissing = true)
public class WinterFrameworkWebControllerAdvice {
	@ExceptionHandler({ServiceException.class})
	public <T> ApiResponse<T> onServiceException(ServiceException e) {
		log.error("onServiceException, ", e);
		return buildResponse(e);
	}

	@ExceptionHandler({NoHandlerFoundException.class})
	public <T> ApiResponse<T> onNoHandlerFoundException(NoHandlerFoundException e) {
		log.error("path: {} not found", e.getRequestURL());
		return buildResponse(ErrorCode.PATH_NOT_FOUND);
	}

	@ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class, HttpMessageNotReadableException.class})
	public <T> ApiResponse<T> onMissingServletRequestParameterException(Exception e) {
		log.error("param error: ", e);
		return buildResponse(ErrorCode.PARAM_ERROR);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public <T> ApiResponse<T> onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("http method not supported: ", e);
		return buildResponse(ErrorCode.METHOD_NOT_SUPPORT);
	}

	@ExceptionHandler({BindException.class})
	public <T> ApiResponse<T> onBindException(BindException e) {
		String msg = e.getBindingResult().getFieldErrors().stream().map(i -> I18n.getOrDefault(i.getObjectName() + "." + i.getField(), i.getField()) + " " + i.getDefaultMessage()).collect(Collectors.joining(", "));
		return buildResponse(ErrorCode.PARAM_ERROR.getCode(), msg);
	}

	@ExceptionHandler(Exception.class)
	public <T> ApiResponse<T> onException(Exception e) {
		log.error("", e);
		if (e instanceof I18nErrorCode err) {
			return buildResponse(err);
		}
		return buildResponse(ErrorCode.SYSTEM_ERROR);
	}

	private <T> ApiResponse<T> buildResponse(int code, String message) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(code);
		apiResponse.setMessage(message);
		apiResponse.setTimestamp(System.currentTimeMillis());
		return apiResponse;
	}

	private <T> ApiResponse<T> buildResponse(I18nErrorCode i18nEnum) {
		return buildResponse(i18nEnum.getCode(), I18n.get(i18nEnum.getI18nCode()));
	}
}
