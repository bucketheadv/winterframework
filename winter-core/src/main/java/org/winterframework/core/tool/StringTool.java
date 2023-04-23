package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sven
 * Created on 2021/12/30 11:01 下午
 */
@UtilityClass
public final class StringTool extends StringUtils {

	public static Integer convert2Int(Object o, Integer defaultVal) {
		return convertNumber(o, defaultVal, () -> Integer.parseInt(o.toString()));
	}

	public static int convert2Int(Object o) {
		return convert2Int(o, 0);
	}

	public static Long convert2Long(Object o, Long defaultVal) {
		return convertNumber(o, defaultVal, () -> Long.parseLong(o.toString()));
	}

	public static long convert2Long(Object o) {
		return convert2Long(o, 0L);
	}

	public static Double convert2Double(Object o, Double defaultVal) {
		return convertNumber(o, defaultVal, () -> Double.parseDouble(o.toString()));
	}

	public static double convert2Double(Object o) {
		return convert2Double(o, 0.0);
	}

	public static Float convert2Float(Object o, Float defaultVal) {
		return convertNumber(o, defaultVal, () -> Float.parseFloat(o.toString()));
	}

	public static float convert2Float(Object o) {
		return convert2Float(o, 0.0f);
	}

	public static Byte convert2Byte(Object o, Byte defaultVal) {
		return convertNumber(o, defaultVal, () -> Byte.parseByte(o.toString()));
	}

	public static byte convert2Byte(Object o) {
		return convert2Byte(o, (byte) 0);
	}

	public static Short convert2Short(Object o, Short defaultVal) {
		return convertNumber(o, defaultVal, () -> Short.valueOf(o.toString()));
	}

	public static short convert2Short(Object o) {
		return convert2Short(o, (short) 0);
	}

	public static String toStringOrNull(Object obj) {
		return null == obj ? null : obj.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Number> T convertNumber(Object o, T defaultVal, ConvertCallback<T> convertCallback) {
		if (o == null) {
			return defaultVal;
		}
		if (defaultVal.getClass().isInstance(o)) {
			return (T) o;
		}
		return convertCallback.invoke();
	}

	@FunctionalInterface
	public interface ConvertCallback<T> {
		T invoke();
	}
}
