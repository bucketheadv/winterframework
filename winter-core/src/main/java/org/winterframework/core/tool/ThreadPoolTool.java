package org.winterframework.core.tool;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/4/26 6:05 PM
 */
public class ThreadPoolTool {
    private ThreadPoolTool() {}

    /**
     * 等待所有线程完成
     * @param futures futures列表
     */
    public static void awaitFuturesComplete(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
