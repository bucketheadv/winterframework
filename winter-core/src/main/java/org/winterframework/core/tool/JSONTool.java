package org.winterframework.core.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author sven
 * Created on 2021/12/30 10:43 下午
 */
@Slf4j
@Component("JSONTool")
public final class JSONTool implements ApplicationContextAware {
    private static ObjectMapper om = new ObjectMapper();

    private JSONTool() {}

    static {
        init(om);
    }

    private static void setDefaultObjectMapper(ObjectMapper objectMapper) {
        if (objectMapper != null) {
            om = objectMapper;
        }
    }

    private static void init(ObjectMapper objectMapper) {
        if (objectMapper != null) {
            // 时间序列化为时间戳
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 未知属性时，不拋出异常
            objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }

    public static String toJSONString(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        try {
            return om.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseList(String str, Class<T> clazz) {
        try {
            return om.readValue(str, om.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V>Map<K, V> parseMap(String str, Class<K> kClass, Class<V> vClass) {
        try {
            return om.readValue(str, om.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convert2Value(Object value, Class<T> clazz) {
        return om.convertValue(value, clazz);
    }

    public static <T> List<T> convert2List(Object value, Class<T> clazz) {
        return om.convertValue(value, om.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        setDefaultObjectMapper(objectMapper);
        init(objectMapper);
    }
}
