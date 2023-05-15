package org.winterframework.crypto.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.experimental.UtilityClass;
import org.winterframework.crypto.annotation.EncryptField;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/11 10:10 PM
 */
@UtilityClass
class CacheUtils {
    private static final Cache<String, Boolean> hasAnnotationClassCache = Caffeine.newBuilder()
            .expireAfterAccess(Duration.of(1, ChronoUnit.HOURS))
            .maximumSize(10_000)
            .build();

    static boolean needToDecrypt(Object object) {
        String className = object.getClass().getName();
        return hasAnnotationClassCache.get(className, k -> {
            Class<?> objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                EncryptField encryptField = field.getAnnotation(EncryptField.class);
                if (Objects.nonNull(encryptField)) {
                    return true;
                }
            }
            return false;
        });
    }
}
