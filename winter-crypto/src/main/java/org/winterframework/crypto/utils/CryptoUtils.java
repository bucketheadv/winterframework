package org.winterframework.crypto.utils;

import org.winterframework.crypto.annotation.EncryptField;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/12 5:40 PM
 */
public class CryptoUtils {
    private CryptoUtils() {}

    public static <T> void encrypt(T paramsObject, String secretKey) throws Exception {
        Field[] declaredFields = paramsObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                if (object instanceof String value) {
                    field.set(paramsObject, AesUtils.aesEncrypt(value, encryptField.algorithm().getCode(), secretKey));
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
                field.setAccessible(true);
                Object object = field.get(result);
                if (object instanceof String value) {
                    field.set(result, AesUtils.aesDecrypt(value, encryptField.algorithm().getCode(), secretKey));
                }
            }
        }
    }
}
