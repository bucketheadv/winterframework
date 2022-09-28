package org.winterframework.jwt.interceptor;

import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.winterframework.core.tool.StringTool;
import org.winterframework.jwt.env.Environment;
import org.winterframework.jwt.support.helper.JwtsHelper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:35 PM
 */
@Slf4j
public class HttpServletHelper {
	public static Environment getEnvironment(HttpServletRequest request) {
		Environment environment = EnvironmentHolder.get();
		if (environment == null) {
			environment = new Environment();
			environment.setPlatform(request.getHeader("platform"));
			environment.setPlatformVersion(request.getHeader("platform-v"));
			environment.setPlatformModel(request.getHeader("platform-model"));
			environment.setPlatformBrand(request.getHeader("platform-brand"));
			environment.setClientVersion(request.getHeader("client-v"));
			environment.setDeviceId(request.getHeader("device-id"));
			environment.setLan(request.getHeader("lang"));
			environment.setToken(getToken(request));
			environment.setIp(getIpAddress(request));
			environment.setImei(request.getHeader("imei"));
			environment.setBundle(request.getHeader("bundle"));
			environment.setCountry(request.getHeader("country"));
			environment.setCarrier(request.getHeader("carrier"));
			environment.setUtmSource(StringTool.defaultString(request.getHeader("utm-source"), request.getHeader("utm_source")));
			environment.setTimezone(request.getHeader("timezone"));
			environment.setOaid(request.getHeader("oaid"));
			environment.setAgent(StringTool.defaultString(request.getHeader("user-agent"), request.getHeader("User-Agent")));
			environment.setAppName(request.getHeader("app-name"));

			try {
				if (StringTool.isNotBlank(environment.getToken())) {
					environment.setUid(Long.parseLong(JwtsHelper.findUid(environment)));
				}
			} catch (Exception ignored) {
			}
			EnvironmentHolder.put(environment);
		}
		return environment;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String address = request.getHeader("x-forwarded-for");
		if (StringTool.isBlank(address) || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("Proxy-Client-IP");
		}
		if (StringTool.isBlank(address) || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringTool.isBlank(address) || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringTool.isBlank(address) || "unknown".equalsIgnoreCase(address)) {
			address = request.getRemoteAddr();
		}
		if (StringTool.isBlank(address)) {
			return address;
		}
		String[] ipArray = address.split(",");
		if (ipArray.length > 1) {
			List<String> arrayList = Lists.newArrayList();
			for (String s : ipArray) {
				String ip = s.trim();
				if (!arrayList.contains(ip)) {
					arrayList.add(ip);
				}
			}
			return StringUtils.join(arrayList, ",");
		}
		return address;
	}

	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader("access-token");
		if (StringTool.isNotBlank(token)) {
			return token;
		}
		token = request.getHeader("access_token");
		if (StringTool.isNotBlank(token)) {
			return token;
		}
		return request.getHeader("token");
	}

	public static void writeResponse(HttpServletResponse response, Object data) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(data != null ? data.toString() : "");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error("writeResponse error, msg: {}", e.getMessage(), e);
		}
	}

	public static String getRequestParam(HttpServletRequest request, Object handler) {
		if (request.getParameterMap().size() > 0) {
			return getRequestForm(request, true);
		} else if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			if (!isRequestBody(method)) {
				log.error("get request param failed, current request is not request body, uri: {}", request.getRequestURI());
				return "";
			} else {
				return getRequestBody(request);
			}
		} else {
			log.error("get request param failed, current request is not handler method, url: {}", request.getRequestURI());
			return "";
		}
	}

	public static String getRequestForm(HttpServletRequest request, boolean deleteS) {
		Map<String, String[]> paramMap = new LinkedHashMap<>(request.getParameterMap());
		if (deleteS) {
			paramMap.remove("s");
		}
		return convertMapToQueryString(paramMap);
	}

	public static String convertMapToQueryString(Map<String, String[]> paramMap) {
		StringBuilder builder = new StringBuilder();
		List<String> keyList = new ArrayList<>(paramMap.keySet());
		Collections.sort(keyList);

		for (int i = 0; i < keyList.size(); i++) {
			builder.append(keyList.get(i)).append("=").append(paramMap.get(keyList.get(i))[0]);
			if (i + 1 != keyList.size()) {
				builder.append("&");
			}
		}
		return builder.toString();
	}

	public static String getRequestBody(HttpServletRequest request) {
		try (BufferedReader reader = request.getReader()) {
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (Exception e) {
			log.error("get request param failed, url: {}, error msg: {}", request.getRequestURI(), e.getMessage(), e);
			return "";
		}
	}

	public static boolean isRequestBody(HandlerMethod method) {
		MethodParameter[] parameters = method.getMethodParameters();
		for (MethodParameter parameter : parameters) {
			Annotation[] annotations = parameter.getParameterAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof RequestBody) {
					return true;
				}
			}
		}
		return false;
	}
}
