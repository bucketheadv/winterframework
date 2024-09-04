package org.winterframework.core.tool;

import java.lang.reflect.ParameterizedType;

/**
 * @author qinglin.liu
 * created at 2024/9/4 16:25
 */
public class ReflectionTool {
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericClass(Class<?> clazz, int index) {
        return (Class<T>) ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }
}
