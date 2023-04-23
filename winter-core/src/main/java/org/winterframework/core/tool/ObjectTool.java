package org.winterframework.core.tool;

import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinglinl
 * Created on 2022/7/27 3:49 下午
 */
@Slf4j
@UtilityClass
public final class ObjectTool extends ObjectUtil {
	public static <T, R> R execute(T t, Callback<T, R> callback) {
		if (t == null) {
			return null;
		}
		try {
			return callback.get(t);
		} catch (NullPointerException e) {
			log.warn("error: {}", e.getMessage());
			return null;
		}
	}

	@FunctionalInterface
	public interface Callback<T, R> {
		R get(T t);
	}
}
