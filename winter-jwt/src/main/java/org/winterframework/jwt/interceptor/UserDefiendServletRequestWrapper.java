package org.winterframework.jwt.interceptor;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.winterframework.jwt.interceptor.stream.ByteArrayServletInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:11 PM
 */
@Slf4j
public class UserDefiendServletRequestWrapper extends HttpServletRequestWrapper {
	private String body;

	public UserDefiendServletRequestWrapper(HttpServletRequest request) {
		super(request);
		if (request.getParameterMap().size() > 0) {
			return;
		}
		body = getHttpServletRequestBody();
	}

	public String getHttpServletRequestBody() {
		StringBuilder builder = new StringBuilder();
		try (InputStream stream = getRequest().getInputStream();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
		)  {
			char[] buffer = new char[128];
			int read;
			while ((read = reader.read(buffer)) > 0) {
				builder.append(buffer, 0, read);
			}
		} catch (Exception e) {
			log.error("user defined http server request wrapper fail, error msg: {}", e.getMessage(), e);
		}
		return builder.toString();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ByteArrayServletInputStream(body.getBytes());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
}
