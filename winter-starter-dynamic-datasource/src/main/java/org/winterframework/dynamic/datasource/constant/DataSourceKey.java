package org.winterframework.dynamic.datasource.constant;

import lombok.experimental.UtilityClass;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 7:01 PM
 */
@UtilityClass
public class DataSourceKey {
    public static final String DEFAULT = "default";

    public static final String POINTCUT = "@within(org.winterframework.dynamic.datasource.annotation.DataSource) || @annotation(org.winterframework.dynamic.datasource.annotation.DataSource)";
}
