package org.winterframework.jwt.interceptor;

import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.exception.ServiceException;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.i18n.enums.ErrorCode;
import org.winterframework.core.tool.JSONTool;
import org.winterframework.core.tool.RegTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.jwt.properties.WinterJwtProperties;
import org.winterframework.jwt.env.Environment;
import org.winterframework.jwt.support.enums.WebErrorCode;
import org.winterframework.jwt.support.helper.JwtsHelper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:06 PM
 */
@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class BasedInterceptor implements AsyncHandlerInterceptor {
	private static HashMap<String, String> params = Maps.newHashMapWithExpectedSize(3);
	@Autowired
	private WinterJwtProperties properties;

	static {
		params.put("apple", "apple-was-foolish");
		params.put("grape", "vitisvinierol0o0");
		params.put("mango", "yellowfruit+-*o0");
		params.put("red", "i2!&0#a8tl7m7*uh");
		params.put("blue", "!ho4cgk9fqe#qd60");
		params.put("green", "*plgkalceuv6kj&v");
	}

	public static void throwsServiceException(I18nErrorCode i18nEnum) {
		throw new ServiceException(i18nEnum);
	}

	public boolean isRiskUser(HttpServletRequest request, HttpServletResponse response, Environment environment) throws Exception {
		return false;
	}

	public void riskControl(HttpServletRequest request, HttpServletResponse response, Environment environment, int errorCode) throws Exception {
	}

	public boolean pre(Environment environment, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
		Environment environment = HttpServletHelper.getEnvironment(request);
		String uri = request.getRequestURI();

		try {
			if (isRiskUser(request, response, environment)) {
				writeResponse(ErrorCode.SYSTEM_ERROR, response);
				return false;
			}
			checkToken(request, environment, uri, handler);
			checkSign(request, environment);
		} catch (ServiceException e) {
			try {
				riskControl(request, response, environment, e.getCode());
			} catch (Exception ex) {
				log.error("risk control failed, url: {}, param: {}, environment: {}, error msg: {}", uri, getRequestParam(request, handler), environment, e.getMessage(), e);
			}
			writeResponse(e, response);
			return false;
		}
		return this.pre(environment, request, response, handler);
	}

	private void writeResponse(I18nErrorCode i18nEnum, HttpServletResponse response) {
		ApiResponse<?> apiResponse = new ApiResponse<>();
		apiResponse.setCode(i18nEnum.getCode());
		apiResponse.setMessage(I18n.get(i18nEnum.getI18nCode()));
		HttpServletHelper.writeResponse(response, JSONTool.toJSONString(apiResponse));
	}

	private void checkToken(HttpServletRequest servletRequest, Environment environment, String uri, Object handler) {
		try {
			if (isCheckToken() && this.isNotMatchUri(uri, getTokenWhiteUriArray())) {
				if (environment.getUid() == null) {
					if (!this.isAllowTokenEmpty() || StringTool.isNotBlank(environment.getToken())) {
						if (StringTool.isBlank(environment.getToken())) {
							throwsServiceException(WebErrorCode.TOKEN_INVALID);
						} else {
							JwtsHelper.check(environment);
						}
					}
				}
			}
		} catch (ServiceException e) {
			log.warn("check token failed, url: {}, param: {}, env: {}", uri, getRequestParam(servletRequest, handler), environment);
			throw e;
		}
	}

	public boolean isCheckToken() {
		return properties.isCheckToken();
	}

	public List<String> getTokenWhiteUriArray() {
		return properties.getTokenWhiteUris();
	}

	public List<String> getSignatureWhiteUriArray() {
		return properties.getSignatureWhiteUris();
	}

	public boolean isAllowTokenEmpty() {
		return properties.isAllowTokenEmpty();
	}

	public boolean isCheckSign() {
		return properties.isCheckSign();
	}

	private void checkSign(HttpServletRequest request, Environment environment) {
		String keyAndPassword = request.getParameter("s");
		if (StringTool.isBlank(keyAndPassword)) {
			keyAndPassword = request.getHeader("s");
		}

		try {
			String uri = request.getRequestURI();
			if (isCheckSign() && isNotMatchUri(uri, getSignatureWhiteUriArray())) {
				Map<String, String[]> paramMap = request.getParameterMap();
				String paramStr = paramMap.size() > 0 ? this.getRequestForm(request) : HttpServletHelper.getRequestBody(request);
				if (StringTool.isNotBlank(paramStr)) {
					if (StringTool.isBlank(keyAndPassword)) {
						log.error("check sign failed, key and password is empty, url: {}, env: {}", uri, environment);
						throwsServiceException(WebErrorCode.SIGN_INVALID);
					} else {
						String[] keyAndPasswordArray = keyAndPassword.split("\\|");
						if (keyAndPasswordArray.length != 2) {
							log.error("check sign failed, key and password is empty, url: {}, env: {}", uri, environment);
							throwsServiceException(WebErrorCode.SIGN_INVALID);
						} else {
							String key = keyAndPasswordArray[0];
							String headerPassword = keyAndPasswordArray[1];
							String serverPassword = hmacSha256(paramStr, params.get(key));
							if (!StringUtils.equals(serverPassword, headerPassword)) {
								log.error("check sign failed, password is not equals, url: {}, params: {}, serverPassword: {}, keyAndPassword: {}, env: {}", uri, paramStr, serverPassword, keyAndPassword, environment);
								throwsServiceException(WebErrorCode.SIGN_INVALID);
							}
						}
					}
				}
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("check sign failed, url: {}, s: {}, env: {}, error msg: {}", request.getRequestURI(), keyAndPassword, environment, e.getMessage(), e);
			throwsServiceException(WebErrorCode.SIGN_INVALID);
		}
	}

	private String getRequestParam(HttpServletRequest request, Object handler) {
		return HttpServletHelper.getRequestParam(request, handler);
	}

	public String getRequestForm(HttpServletRequest request) {
		Map<String, String[]> paramMap = new HashMap<>(request.getParameterMap());
		paramMap.remove("s");
		return HttpServletHelper.convertMapToQueryString(paramMap);
	}

	private boolean isNotMatchUri(String path, List<String> urls) {
		return !RegTool.isMatch(path, urls);
	}

	private String hmacSha256(String data, String key) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		Mac hmacSha256 = Mac.getInstance("HmacSHA256");
		hmacSha256.init(secretKey);
		byte[] array = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
		StringBuilder builder = new StringBuilder();

		for (byte item : array) {
			builder.append(Integer.toHexString(item & 255 | 256), 1, 3);
		}

		return builder.toString().toUpperCase();
	}
}
