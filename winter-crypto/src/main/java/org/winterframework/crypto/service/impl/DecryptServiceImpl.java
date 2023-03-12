package org.winterframework.crypto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.winterframework.crypto.utils.AesUtils;
import org.winterframework.crypto.annotation.EncryptField;
import org.winterframework.crypto.service.DecryptService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/11 8:40 PM
 */
@Service
public class DecryptServiceImpl implements DecryptService {
    @Autowired
    private AesUtils aesUtils;

    @Value("${winter.crypto.key:}")
    private String key;
    @Override
    public <T> T decrypt(T result) throws Exception {
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                if (object instanceof String value) {
                    field.set(result, aesUtils.aesDecrypt(value, key));
                }
            }
        }
        return result;
    }
}
