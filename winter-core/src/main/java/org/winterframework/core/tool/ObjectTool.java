package org.winterframework.core.tool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qinglinl
 * Created on 2022/7/27 3:49 下午
 */
@Slf4j
public final class ObjectTool {
	public static <T, R> R execute(T t, Callback<T, R> callback) {
		if (t == null) {
			return null;
		}
		try {
			return callback.get(t);
		} catch (NullPointerException e) {
			log.warn("TryTool#invoke error: {}", e.getMessage());
			return null;
		}
	}

	@FunctionalInterface
	public interface Callback<T, R> {
		R get(T t);
	}
}
