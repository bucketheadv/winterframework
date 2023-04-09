package org.winterframework.crypto.interceptor;

import org.winterframework.crypto.annotation.EncryptField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/11 10:10 PM
 */
class CacheUtils {
    private static final Map<String, Boolean> hasAnnotationClassMap = new HashMap<>();

    static boolean needToDecrypt(Object object) {
        String className = object.getClass().getName();
        if (hasAnnotationClassMap.containsKey(className)) {
            return hasAnnotationClassMap.get(className);
        }
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                hasAnnotationClassMap.put(className, true);
                return true;
            }
        }
        hasAnnotationClassMap.put(className, false);
        return false;
    }
}
