package org.winterframework.core.configure.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.winterframework.core.api.Errorable;
import org.winterframework.core.exception.ServiceException;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.support.enums.ErrorCode;

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
	public ApiResponse<?> onServiceException(ServiceException e) {
		return buildResponse(e);
	}

	@ExceptionHandler({NoHandlerFoundException.class})
	public ApiResponse<?> onNoHandlerFoundException(NoHandlerFoundException e) {
		log.error("path: {} not found", e.getRequestURL());
		return buildResponse(ErrorCode.PATH_NOT_FOUND);
	}

	@ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class, HttpMessageNotReadableException.class})
	public ApiResponse<?> onMissingServletRequestParameterException(Exception e) {
		log.error("param error: ", e);
		return buildResponse(ErrorCode.PARAM_ERROR);
	}

	@ExceptionHandler({BindException.class})
	public ApiResponse<?> onBindException(BindException e) {
		String msg = e.getBindingResult().getFieldErrors().stream().map(i -> I18n.getOrDefault(i.getObjectName() + "." + i.getField(), i.getField()) + " " + i.getDefaultMessage()).collect(Collectors.joining(", "));
		return buildResponse(ErrorCode.PARAM_ERROR.getCode(), msg);
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse<?> onException(Exception e) {
		log.error("onException: ", e);
		if (e instanceof Errorable) {
			Errorable err = (Errorable) e;
			return buildResponse(err);
		}
		return buildResponse(ErrorCode.SYSTEM_ERROR);
	}

	private <T> ApiResponse<T> buildResponse(int code, String message) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(code);
		apiResponse.setMsg(message);
		apiResponse.setTimestamp(System.currentTimeMillis());
		return apiResponse;
	}

	private <T> ApiResponse<T> buildResponse(Errorable errorable) {
		return buildResponse(errorable.getCode(), errorable.getMessage());
	}
}
