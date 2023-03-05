package org.winterframework.crypto.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.winterframework.crypto.serializer.SensitiveJsonSerializer;
import org.winterframework.crypto.strategy.SensitiveStrategy;

import java.lang.annotation.*;

/**
 * @author sven
 * Created on 2023/3/5 11:02 PM
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {
    SensitiveStrategy strategy();
}
