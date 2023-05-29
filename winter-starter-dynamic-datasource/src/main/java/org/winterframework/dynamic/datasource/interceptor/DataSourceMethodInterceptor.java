package org.winterframework.dynamic.datasource.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.winterframework.dynamic.datasource.annotation.DataSource;
import org.winterframework.dynamic.datasource.constant.DataSourceKey;
import org.winterframework.dynamic.datasource.helper.DataSourceHolder;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/16 11:36 PM
 */
@Slf4j
public class DataSourceMethodInterceptor implements MethodInterceptor {
    @Nullable
    @Override
    public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
        try {
            DataSource dataSource = AnnotationUtils.findAnnotation(invocation.getMethod(), DataSource.class);
            if (dataSource == null) {
                dataSource = AnnotationUtils.findAnnotation(invocation.getClass(), DataSource.class);
                if (dataSource == null) {
                    Object proxy = invocation.getThis();
                    if (proxy != null) {
                        dataSource = AnnotationUtils.findAnnotation(proxy.getClass(), DataSource.class);
                        if (dataSource == null) {
                            dataSource = AnnotationUtils.findAnnotation(proxy.getClass().getSuperclass(), DataSource.class);
                        }
                    }
                }
            }
            String dataSourceName;
            if (dataSource == null) {
                log.info("使用默认数据源: {}", DataSourceKey.DEFAULT);
                dataSourceName = DataSourceKey.DEFAULT;
            } else {
                dataSourceName = dataSource.value();
                log.info("获取数据源: {}", dataSourceName);
            }
            DataSourceHolder.setDataSource(dataSourceName);
            return invocation.proceed();
        } finally {
            DataSourceHolder.clearDataSource();
        }
    }
}
