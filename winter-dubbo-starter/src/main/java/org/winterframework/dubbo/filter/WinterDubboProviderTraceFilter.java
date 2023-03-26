package org.winterframework.dubbo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;
import org.winterframework.core.tool.StringTool;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.tool.MDCTool;

/**
 * @author sven
 * Created on 2023/3/26 8:45 PM
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class WinterDubboProviderTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(TraceConstants.TRACE_KEY);
        if (StringTool.isBlank(traceId)) {
            traceId = MDCTool.getOrCreateTraceId(TraceConstants.TRACE_KEY);
        }
        MDC.put(TraceConstants.TRACE_KEY, traceId);
        log.info("Provider端执行invoke设置{}", TraceConstants.TRACE_KEY);
        try {
            return invoker.invoke(invocation);
        } finally {
            MDC.clear();
        }
    }
}
