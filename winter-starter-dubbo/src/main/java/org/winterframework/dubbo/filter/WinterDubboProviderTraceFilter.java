package org.winterframework.dubbo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;
import org.winterframework.core.tool.StringTool;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.tool.UUIDTool;

import java.util.Arrays;

/**
 * @author sven
 * Created on 2023/3/26 8:45 PM
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class WinterDubboProviderTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(TraceConstants.TRACE_ID);
        if (StringTool.isBlank(traceId)) {
            traceId = UUIDTool.getOrCreateTraceId(TraceConstants.TRACE_ID);
        }
        MDC.put(TraceConstants.TRACE_ID, traceId);
        try {
            Result result = invoker.invoke(invocation);
            log.info("请求参数: {}, 返回结果: {}", Arrays.toString(invocation.getArguments()), result.getValue());
            return result;
        } catch (Exception e) {
            log.info("请求异常: {}", Arrays.toString(invocation.getArguments()), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }
}
