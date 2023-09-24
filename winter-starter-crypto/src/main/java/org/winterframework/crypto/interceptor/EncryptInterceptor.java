package org.winterframework.crypto.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.winterframework.crypto.properties.WinterCryptoProperties;
import org.winterframework.crypto.tool.CryptoTool;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author sven
 * Created on 2023/3/11 1:50 PM
 */
@Component
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)
})
public class EncryptInterceptor implements Interceptor {
    @Autowired
    private WinterCryptoProperties winterCryptoProperties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Object parameterObject = parameterHandler.getParameterObject();
        if (parameterObject instanceof MapperMethod.ParamMap<?> paramMap) {
            // 修改时
            Object v = paramMap.values().iterator().next();
            CryptoTool.encrypt(v, winterCryptoProperties.getSecretKeyMap());
            Object result = invocation.proceed();
            // 加密后保存完成后再解密回去
            CryptoTool.decrypt(v, winterCryptoProperties.getSecretKeyMap());
            return result;
        } else {
            // 新增时
            if (parameterObject != null) {
                CryptoTool.encrypt(parameterObject, winterCryptoProperties.getSecretKeyMap());
                Object result = invocation.proceed();
                // 加密后保存完成后再解密回去
                CryptoTool.decrypt(parameterObject, winterCryptoProperties.getSecretKeyMap());
                return result;
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
