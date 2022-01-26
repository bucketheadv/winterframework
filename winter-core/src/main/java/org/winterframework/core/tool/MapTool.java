package org.winterframework.core.tool;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinglinl
 * Created on 2022/1/26 6:17 下午
 */
@Slf4j
public class MapTool {
    /**
     * 通过jsonpath获取map路径数据
     * @param data 数据对象
     * @param jsonPath 路径，如 a.b.c
     * @param defaultValue 为空时返回默认值
     * @param <T> 默认值类型
     * @return T
     */
    public static <T> T tryDeepGet(Object data, String jsonPath, T defaultValue) {
        try {
            return JsonPath.read(data, "$." + jsonPath);
        } catch (PathNotFoundException ex) {
            log.error("MapTool#tryDeepGet error, stacktrace: ", ex);
            return defaultValue;
        }
    }
}
