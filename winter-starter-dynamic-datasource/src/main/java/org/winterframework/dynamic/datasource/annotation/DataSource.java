package org.winterframework.dynamic.datasource.annotation;

import org.winterframework.dynamic.datasource.constant.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:19 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataSource {
    /**
     * 数据源名称
     * @return
     */
    String value();
}
