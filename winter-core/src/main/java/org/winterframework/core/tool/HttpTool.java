package org.winterframework.core.tool;

import okhttp3.*;
import org.winterframework.core.exception.ServiceException;
import org.winterframework.core.support.enums.ErrorCode;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/11/1 10:28 AM
 */
public class HttpTool {
	private static final Duration timeout = Duration.ofSeconds(30);

	private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
			.readTimeout(timeout)
			.writeTimeout(timeout)
			.callTimeout(timeout)
			.build();

	public static <T> T get(String url, Class<T> clazz) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		return execute(request, clazz);
	}

	public static <T> T postJSON(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> clazz) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		String postBody = JSONTool.toJSONString(params);
		Request.Builder builder = new Request.Builder().url(url);
		if (CollectionTool.isEmpty(headers)) {
			headers = new HashMap<>();
		}
		headers.putIfAbsent("Accept", "application/json;charset=utf-8");
		if (CollectionTool.isNotEmpty(headers)) {
			headers.forEach((k, v) -> builder.addHeader(k, StringTool.toStringOrNull(v)));
		}
		Request request = builder.post(RequestBody.create(postBody, mediaType)).build();
		return execute(request, clazz);
	}

	public static <T> T postJSON(String url, Map<String, Object> params, Class<T> clazz) throws IOException {
		return postJSON(url, params, new HashMap<>(), clazz);
	}

	public static String postJSON(String url, Map<String, Object> params, Map<String, Object> headers) throws IOException {
		return postJSON(url, params, headers, String.class);
	}

	public static <T> T postEncodedForm(String url, Map<String, Object> params, Class<T> clazz) throws IOException {
		FormBody.Builder builder = new FormBody.Builder();
		if (CollectionTool.isNotEmpty(params)) {
			params.forEach((k, v) -> builder.add(k, StringTool.toStringOrNull(v)));
		}
		Request request = new Request.Builder()
				.post(builder.build())
				.url(url)
				.build();
		return execute(request, clazz);
	}

	public static <T> T postFormData(String url, Map<String, Object> params, Class<T> clazz) throws IOException {
		MediaType mediaType = MediaType.parse("multipart/form-data");
		assert mediaType != null;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(mediaType);
		if (CollectionTool.isNotEmpty(params)) {
			params.forEach((k, v) -> builder.addFormDataPart(k, StringTool.toStringOrNull(v)));
		}

		Request request = new Request.Builder()
				.post(builder.build())
				.url(url)
				.build();
		return execute(request, clazz);
	}

	public static <T> T execute(Request request, Class<T> clazz) throws IOException {
		try (Response response = okHttpClient.newCall(request).execute();
			 ResponseBody body = response.body()) {
			if (response.code() != 200) {
				assert response.body() != null;
				throw new ServiceException(ErrorCode.PARAM_ERROR);
			}
			if (body != null) {
				String str = body.string();
				if (clazz == String.class) {
					return cast(str);
				}
				return JSONTool.parseObject(str, clazz);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> T cast(Object v) {
		return (T) v;
	}
}
