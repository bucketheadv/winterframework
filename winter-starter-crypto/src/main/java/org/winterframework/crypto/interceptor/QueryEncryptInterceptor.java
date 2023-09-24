package org.winterframework.crypto.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 拦截mybatis query查询
 * @author qinglin.liu
 * @create 2023/9/23 16:52
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class QueryEncryptInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        Object target = invocation.getTarget();
//        Method method = invocation.getMethod();
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap<?>) args[1];
        return invocation.proceed();
    }
}
