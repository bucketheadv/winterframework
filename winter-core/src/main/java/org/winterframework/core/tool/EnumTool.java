package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author qinglinl
 * Created on 2022/10/24 2:58 PM
 */
@Slf4j
@UtilityClass
public class EnumTool {
	private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

	public static <T extends Enum<T>, R> T getEnum(SFunction<T, R> func, R val) {
		if (val == null) {
			return null;
		}
		try {
			T[] constants = getEnum(func).getEnumConstants();
			for (T constant : constants) {
				if (val.equals(func.apply(constant))) {
					return constant;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T, R> Class<T> getEnum(Function<T, R> func) throws Exception {
		Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
		ReflectionUtils.makeAccessible(writeReplace);
		SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(func);
		Class<?> clazz = map.get(serializedLambda.getImplClass());
		if (clazz == null) {
			clazz = Class.forName(serializedLambda.getImplClass().replace("/", "."));
			map.put(serializedLambda.getImplClass(), clazz);
		}
		return (Class<T>) clazz;
	}

	@FunctionalInterface
	public interface SFunction<T, R> extends Function<T, R>, Serializable {}
}
