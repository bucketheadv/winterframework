package org.winterframework.crypto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.winterframework.crypto.annotation.EncryptField;
import org.winterframework.crypto.service.EncryptService;
import org.winterframework.crypto.utils.AesUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/11 2:34 PM
 */
@Service
public class EncryptServiceImpl implements EncryptService {
    @Autowired
    private AesUtils aesUtils;

    @Value("${winter.crypto.key:}")
    private String key;
    @Override
    public <T> T encrypt(T paramsObject) throws Exception {
        Field[] declaredFields = paramsObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (Objects.nonNull(encryptField)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                if (object instanceof String value) {
                    field.set(paramsObject, aesUtils.aesEncrypt(value, key));
                }
            }
        }
        return paramsObject;
    }
}
