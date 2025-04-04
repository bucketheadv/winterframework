package org.winterframework.web.configuration.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.enums.ErrorCode;
import org.winterframework.core.i18n.exception.ServiceException;
import org.winterframework.core.support.ApiData;

import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:41 PM
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(prefix = "winter.web.advice", value = "enabled", havingValue = "true", matchIfMissing = true)
public class WinterFrameworkWebControllerAdvice {
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ServiceException.class})
	public <T> ApiData<T> onServiceException(ServiceException e) {
		log.error("{}", ExceptionUtils.getStackTrace(e));
		return buildResponse(e);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoResourceFoundException.class})
	public <T> ApiData<T> onNoResourceFoundException(NoResourceFoundException e) {
		log.error("{}", e.getMessage());
		return buildResponse(ErrorCode.PATH_NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoHandlerFoundException.class})
	public <T> ApiData<T> onNoHandlerFoundException(NoHandlerFoundException e) {
		log.error("path: {} not found", e.getRequestURL());
		return buildResponse(ErrorCode.PATH_NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class, HttpMessageNotReadableException.class})
	public <T> ApiData<T> onMissingServletRequestParameterException(Exception e) {
		log.error("{}", ExceptionUtils.getStackTrace(e));
		return buildResponse(ErrorCode.PARAM_ERROR);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public <T> ApiData<T> onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("{}", ExceptionUtils.getStackTrace(e));
		return buildResponse(ErrorCode.METHOD_NOT_SUPPORT);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BindException.class})
	public <T> ApiData<T> onBindException(BindException e) {
		String msg = e.getBindingResult().getFieldErrors().stream().map(i -> I18n.getOrDefault(i.getObjectName() + "." + i.getField(), i.getField()) + " " + i.getDefaultMessage()).collect(Collectors.joining(", "));
		return buildResponse(ErrorCode.PARAM_ERROR.getCode(), msg);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public <T> ApiData<T> onException(Exception e) {
		log.error("{}", ExceptionUtils.getStackTrace(e));
		if (e instanceof I18nErrorCode err) {
			return buildResponse(err);
		}
		return buildResponse(ErrorCode.SYSTEM_ERROR);
	}

	private <T> ApiData<T> buildResponse(int code, String message) {
		ApiData<T> apiData = new ApiData<>();
		apiData.setCode(code);
		apiData.setMessage(message);
		apiData.setTimestamp(System.currentTimeMillis());
		return apiData;
	}

	private <T> ApiData<T> buildResponse(I18nErrorCode i18nEnum) {
		return buildResponse(i18nEnum.getCode(), I18n.get(i18nEnum.getI18nCode()));
	}
}
