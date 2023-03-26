package org.winterframework.trace.tool;

import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;
import org.winterframework.core.tool.StringTool;

/**
 * @author sven
 * Created on 2023/3/24 9:36 PM
 */
public class MDCTool {
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
