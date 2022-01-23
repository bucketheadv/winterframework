package org.winterframework.core.tool;

import java.util.Date;

/**
 * @author sven
 * Created on 2021/12/30 11:02 下午
 */
public final class DateTool {
    public static long currentTimeMills() {
        return System.currentTimeMillis();
    }

    public static Date now() {
        return new Date(currentTimeMills());
    }
}
