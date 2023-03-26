package org.winterframework.dubbo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.winterframework.trace.constant.TraceConstants;
import org.winterframework.trace.tool.MDCTool;

/**
 * @author sven
 * Created on 2023/3/26 8:45 PM
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER)
public class WinterDubboConsumerTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("Consumer端执行invoke设置{}", TraceConstants.TRACE_KEY);
        invocation.setAttachmentIfAbsent(TraceConstants.TRACE_KEY, MDCTool.getOrCreateTraceId(TraceConstants.TRACE_KEY));
        return invoker.invoke(invocation);
    }
}
