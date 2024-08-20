package org.winterframework.jwt.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.support.ApiResponse;
import org.winterframework.core.i18n.enums.ErrorCode;
import org.winterframework.core.tool.JsonTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.jwt.env.Environment;

import java.io.IOException;
import java.util.UUID;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:21 PM
 */
@Slf4j
@NoArgsConstructor
@WebFilter
@Component
public class UserDefinedFilter implements Filter {
	private String[] allowAgentArray;

	/**
	 * 需要打印耗时时长的时间
	 */
	private long costTime = 200;

	public UserDefinedFilter(long costTime) {
		this.costTime = costTime;
		this.allowAgentArray = new String[]{"CFNetwork".toLowerCase(), "okhttp"};
	}

	public UserDefinedFilter(long costTime, String... allowAgent) {
		this.costTime = costTime;
		this.allowAgentArray = allowAgent;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		long currentTime = System.currentTimeMillis();
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		Environment env = HttpServletHelper.getEnvironment(httpServletRequest);
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		try {
			MDC.put("code", "0");
			MDC.put("uri", httpServletRequest.getRequestURI());
			MDC.put("deviceId", env.getDeviceId());
			MDC.put("platform", env.getPlatform());
			MDC.put("platformBrand", env.getPlatformBrand());
			MDC.put("platformModel", env.getPlatformModel());
			MDC.put("platformVersion", env.getPlatformVersion());
			MDC.put("bundle", env.getBundle());
			MDC.put("networkType", env.getNetworkType());
			MDC.put("ip", env.getIp());
			MDC.put("clientVersion", env.getClientVersion());
			MDC.put("utmSource", env.getUtmSource());
			MDC.put("uid", env.getUid() != null ? env.getUid().toString() : "");
			if (StringTool.isBlank(env.getPlatform())) {
				String requestId = StringTool.defaultString(httpServletRequest.getHeader("request-id"), UUID.randomUUID().toString().replaceAll("-", ""));
				MDC.put("requestId", requestId);
			}
			wrapperDoFilter(httpServletRequest, httpServletResponse, filterChain);
		} catch (Exception e) {
			log.error("user defined filter doFilter failed, uri: {}, error msg: {}, env: {}", httpServletRequest.getRequestURI(), e.getMessage(), env, e);
			HttpServletHelper.writeResponse(httpServletResponse, ApiResponse.builder()
							.code(ErrorCode.SYSTEM_ERROR.getCode())
							.message(I18n.get(ErrorCode.SYSTEM_ERROR.getI18nCode()))
					.build());
		} finally {
			EnvironmentHolder.clear();
			long currentCostTime = System.currentTimeMillis() - currentTime;
			if (costTime <= 0 || currentCostTime > costTime) {
				log.info("current request is too longer time, gzip: {}, uri: {}, cost time: {}, env: {}", isGzip(httpServletRequest), httpServletRequest.getRequestURI(), currentCostTime, JsonTool.toJsonString(env));
			}
			MDC.clear();
		}
	}

	private void wrapperDoFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletRequest wrapper = new UserDefinedServletRequestWrapper(request);
		chain.doFilter(wrapper, response);
	}

	private boolean isGzip(HttpServletRequest request) {
		String encoding = request.getHeader("content-encoding");
		if (StringTool.isBlank(encoding)) {
			encoding = request.getHeader("Content-Encoding");
		}
		return StringUtils.indexOfIgnoreCase(encoding, "gzip") != -1;
	}
}
