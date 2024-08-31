package org.winterframework.trace.tool;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;
import org.winterframework.core.tool.StringTool;

import java.util.UUID;

/**
 * @author sven
 * Created on 2023/3/24 9:36 PM
 */
@UtilityClass
public class UUIDTool {
    public static String getOrCreateTraceId(String key) {
        String value = MDC.get(key);
        if (StringTool.isBlank(value)) {
            value = uuid();
        }
        return value;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
