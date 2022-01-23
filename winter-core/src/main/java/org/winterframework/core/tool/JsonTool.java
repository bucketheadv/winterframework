package org.winterframework.core.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sven
 * Created on 2021/12/30 10:43 下午
 */
@Slf4j
@Component
public final class JsonTool implements ApplicationContextAware {
    private static ObjectMapper om = new ObjectMapper();

    private JsonTool() {}

    public static String toJSONString(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JsonTool#toJSONString异常:", e);
        }
        return null;
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        try {
            return om.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            log.error("JsonTool#parseObject异常:", e);
        }
        return null;
    }

    public static <T> List<T> parseList(String str, Class<T> clazz) {
        try {
            return om.readValue(str, new TypeReference<ArrayList<T>>() {});
        } catch (JsonProcessingException e) {
            log.error("JsonTool#parseList异常:", e);
        }
        return null;
    }

    public static <T> T convert2Value(Object value, Class<T> clazz) {
        try {
            return om.convertValue(value, clazz);
        } catch (IllegalArgumentException e) {
            log.error("JsonTool#convert2Value异常:", e);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        if (objectMapper != null) {
            om = objectMapper;
        }
    }
}
