package org.winterframework.dynamic.datasource.helper;

import lombok.experimental.UtilityClass;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:49 PM
 */
@UtilityClass
public final class DataSourceHolder {
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        HOLDER.set(dataSource);
    }

    public static String getDataSource() {
        return HOLDER.get();
    }

    public static void clearDataSource() {
        HOLDER.remove();
    }
}
