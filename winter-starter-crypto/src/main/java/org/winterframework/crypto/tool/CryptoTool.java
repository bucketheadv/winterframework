package org.winterframework.crypto.tool;

import lombok.experimental.UtilityClass;
import org.springframework.util.ReflectionUtils;
import org.winterframework.crypto.annotation.EncryptField;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/12 5:40 PM
 */
@UtilityClass
public class CryptoTool {
    public static <T> void encrypt(T paramsObject, Map<String, String> secretKeyMap) throws Exception {
        Field[] declaredFields = paramsObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                ReflectionUtils.makeAccessible(field);
                Object object = field.get(paramsObject);
                if (object instanceof String value) {
                    ReflectionUtils.setField(field, paramsObject, AesTool.encrypt(value, encryptField.algorithm().getCode(), secretKeyMap.get(encryptField.key())));
                }
            }
        }
    }

    public static <T> void decrypt(T result, Map<String, String> secretKeyMap) throws Exception {
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                ReflectionUtils.makeAccessible(field);
                Object object = field.get(result);
                if (object instanceof String value) {
                    ReflectionUtils.setField(field, result, AesTool.decrypt(value, encryptField.algorithm().getCode(), secretKeyMap.get(encryptField.key())));
                }
            }
        }
    }
}
