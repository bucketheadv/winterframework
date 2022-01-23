package org.winterframework.core.support;

import lombok.Data;

/**
 * @author sven
 * Created on 2022/1/11 8:45 下午
 */
@Data
public final class BooleanPair<T> {
    private boolean success;

    private String msg;

    private T data;

    public BooleanPair() {
        this.success = true;
    }

    public BooleanPair(T data) {
        this.success = true;
        this.data = data;
    }

    public BooleanPair(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public BooleanPair(boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public static <T> BooleanPair<T> fail(String msg) {
        return new BooleanPair<>(false, null, msg);
    }

    public static <T> BooleanPair<T> fail() {
        return fail(null);
    }

    public static <T> BooleanPair<T> success(T data) {
        return new BooleanPair<>(true, data);
    }

    public static <T> BooleanPair<T> success() {
        return success(null);
    }
}
