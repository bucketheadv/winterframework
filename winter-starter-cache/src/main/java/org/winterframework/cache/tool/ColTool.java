package org.winterframework.cache.tool;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author qinglinl
 * Created on 2022/10/27 2:32 PM
 */
public class ColTool {
	public static Object getColValue(Object data, String col) {
		Field field = ReflectionUtils.findField(data.getClass(), col);
		assert field != null;
		field.setAccessible(true);
		return ReflectionUtils.getField(field, data);
	}
}
