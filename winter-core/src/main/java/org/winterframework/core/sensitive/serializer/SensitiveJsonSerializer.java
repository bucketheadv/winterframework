package org.winterframework.core.sensitive.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.winterframework.core.sensitive.annotation.Sensitive;
import org.winterframework.core.sensitive.strategy.SensitiveStrategy;

import java.io.IOException;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/5 11:08 PM
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private SensitiveStrategy strategy;
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(strategy.desensitizer().apply(s));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
        if (Objects.nonNull(sensitive) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            this.strategy = sensitive.strategy();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
