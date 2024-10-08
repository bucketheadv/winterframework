package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @author qinglinl
 * Created on 2022/7/27 3:49 下午
 */
@Slf4j
@UtilityClass
public final class ObjectTool {
	public static <T, R> R execute(T t, Function<T, R> callback) {
		if (t == null) {
			return null;
		}
		try {
			return callback.apply(t);
		} catch (NullPointerException e) {
			log.warn("error: {}", e.getMessage());
			return null;
		}
	}
}
