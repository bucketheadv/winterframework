package org.winterframework.crypto.annotation;

import org.winterframework.crypto.enums.AlgorithmEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sven
 * Created on 2023/3/11 8:35 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
    /**
     * 加密算法
     */
    AlgorithmEnum algorithm() default AlgorithmEnum.AES;

    /**
     * 密钥的key, 取winter.crypto的配置下的key值
     */
    String key() default "default";
}
