package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.winterframework.core.support.BooleanPair;

import java.util.function.Function;

/**
 * @author sven
 * Created on 2022/1/11 8:49 下午
 */
@UtilityClass
public final class RetryTool {
    public static <T> BooleanPair<T> execute(int maxAttempts, Function<Integer, BooleanPair<T>> callback) {
        for (int i = 0; i < maxAttempts; i++) {
            BooleanPair<T> result = callback.apply(i + 1);
            if (result.isSuccess()) {
                return result;
            }
        }
        return BooleanPair.fail();
    }
}
