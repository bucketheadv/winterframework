package org.winterframework.gateway.filter;

import com.google.common.collect.Maps;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.winterframework.core.i18n.I18n;
import org.winterframework.core.i18n.enums.ErrorCode;

import java.util.Map;

/**
 * @author qinglin.liu
 * created at 2023/11/26 17:52
 */
public abstract class AbstractExceptionHandler {
    protected String formatMessage(Throwable ex) {
        String errorMessage;
        if (ex instanceof NotFoundException) {
            errorMessage = ((NotFoundException)ex).getReason();
        } else if (ex instanceof ResponseStatusException responseStatusException) {
            errorMessage = responseStatusException.getMessage();
        } else {
            errorMessage = ex.getMessage();
        }
        return errorMessage;
    }

    protected Map<String, Object> buildErrorMap(String lang) {
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("code", ErrorCode.SYSTEM_ERROR.getCode());
        resMap.put("message", I18n.get(ErrorCode.SYSTEM_ERROR.getI18nCode()));
        return resMap;
    }
}
