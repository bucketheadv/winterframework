package org.winterframework.core.tool;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.Language;

/**
 * @author qinglinl
 * Created on 2022/1/26 6:17 下午
 */
@Slf4j
@UtilityClass
public final class JsonPathTool {
    /**
     * 通过jsonpath获取map路径数据
     * @param data 数据对象
     * @param jsonPath 路径，如 a.b.c
     * @param defaultValue 为空时返回默认值
     * @param <T> 默认值类型
     * @return T
     */
    public static <T> T tryRead(Object data, @Language("jsonpath") String jsonPath, T defaultValue) {
        try {
            T result = JsonPath.read(data, jsonPath);
            return result == null ? defaultValue : result;
        } catch (PathNotFoundException ex) {
            log.error("", ex);
            return defaultValue;
        }
    }
}
