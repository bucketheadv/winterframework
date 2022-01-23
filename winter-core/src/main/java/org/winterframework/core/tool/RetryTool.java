package org.winterframework.core.tool;

import org.winterframework.core.support.BooleanPair;

/**
 * @author sven
 * Created on 2022/1/11 8:49 下午
 */
public final class RetryTool {
    private RetryTool() {}

    public static <T> BooleanPair<T> execute(int maxAttempts, Callback<T> callback) {
        for (int i = 0; i < maxAttempts; i++) {
            BooleanPair<T> result = callback.invoke(i + 1);
            if (result.isSuccess()) {
                return result;
            }
        }
        return BooleanPair.fail();
    }

    @FunctionalInterface
    public interface Callback<T> {
        BooleanPair<T> invoke(int retryAttempts);
    }
}
