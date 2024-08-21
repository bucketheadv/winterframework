package org.winterframework.core.support.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.NonNull;

import java.util.concurrent.*;

/**
 * 通过信号量控制线程同时执行数量
 * @author qinglinl
 * Created on 2022/3/2 10:30 上午
 */
@Slf4j
public class SemaphoreThreadPool implements ApplicationListener<ContextClosedEvent> {
    private final Semaphore semaphore;

    private final ThreadPoolExecutor pool;

    private final int threadCount;

    private static final int CPU_CORE_SIZE = Runtime.getRuntime().availableProcessors();

    public SemaphoreThreadPool(int threadCount) {
        this.threadCount = threadCount;
        this.semaphore = new Semaphore(threadCount);
        this.pool = new ThreadPoolExecutor(threadCount, threadCount, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }

    /**
     * 创建CPU密集型线程池
     */
    public static SemaphoreThreadPool newCPUIntensive() {
        return new SemaphoreThreadPool(CPU_CORE_SIZE + 1);
    }

    /**
     * 创建IO密集型线程池
     */
    public static SemaphoreThreadPool newIOIntensive() {
        return new SemaphoreThreadPool(CPU_CORE_SIZE * 2);
    }

    public void execute(Runnable command) {
        try {
            semaphore.acquire();
            pool.execute(() -> {
                try {
                    command.run();
                } catch (Exception e) {
                    log.error("execute command run error, msg: ", e);
                } finally {
                    semaphore.release();
                }
            });
        } catch (InterruptedException e) {
            log.error("execute error, msg: ", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        try {
            pool.shutdown();
            releaseAll();
        } catch (Exception e) {
            log.error("shutdown error, msg: ", e);
        }
    }

    /**
     * 等待所有线程完成
     */
    public void await() {
        try {
            semaphore.acquire(threadCount);
        } catch (InterruptedException e) {
            log.error("await error, msg: ", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 释放所有可请求数，使线程池可以重新被使用
     */
    public void releaseAll() {
        semaphore.release(threadCount);
    }

    @Override
    public void onApplicationEvent(@NonNull ContextClosedEvent event) {
        log.info("应用程序关闭，正在清理线程池...");
        // 关闭时清理线程池
        pool.shutdown();
        try {
            // 等待线程池结束
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
