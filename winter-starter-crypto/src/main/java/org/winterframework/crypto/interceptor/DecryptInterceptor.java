package org.winterframework.crypto.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.winterframework.crypto.properties.WinterCryptoProperties;
import org.winterframework.crypto.tool.CryptoTool;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author sven
 * Created on 2023/3/11 2:00 PM
 */
@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class DecryptInterceptor implements Interceptor {
    @Autowired
    private WinterCryptoProperties winterCryptoProperties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }

        if (resultObject instanceof ArrayList<?> resultList) {
            if (!CollectionUtils.isEmpty(resultList) && CacheUtils.needToDecrypt(resultList.get(0))) {
                for (Object o : resultList) {
                    CryptoTool.decrypt(o, winterCryptoProperties.getSecretKeyMap());
                }
            }
        } else {
            if (CacheUtils.needToDecrypt(resultObject)) {
                CryptoTool.decrypt(resultObject, winterCryptoProperties.getSecretKeyMap());
            }
        }
        return resultObject;
    }
}
