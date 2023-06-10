package org.winterframework.crypto.tool;

import lombok.experimental.UtilityClass;
import org.springframework.util.ReflectionUtils;
import org.winterframework.crypto.annotation.EncryptField;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/12 5:40 PM
 */
@UtilityClass
public class CryptoTool {
    public static <T> void encrypt(T paramsObject, String secretKey) throws Exception {
        Field[] declaredFields = paramsObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                ReflectionUtils.makeAccessible(field);
                Object object = field.get(paramsObject);
                if (object instanceof String value) {
                    ReflectionUtils.setField(field, paramsObject, AesTool.aesEncrypt(value, encryptField.algorithm().getCode(), secretKey));
                }
            }
        }
    }

    public static <T> void decrypt(T result, String secretKey) throws Exception {
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                ReflectionUtils.makeAccessible(field);
                Object object = field.get(result);
                if (object instanceof String value) {
                    ReflectionUtils.setField(field, result, AesTool.aesDecrypt(value, encryptField.algorithm().getCode(), secretKey));
                }
            }
        }
    }
}
