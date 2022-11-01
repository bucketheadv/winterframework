package org.winterframework.core.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sven
 * Created on 2022/1/11 8:45 下午
 */
@Getter
@Setter
public final class BooleanPair<T> implements Serializable {
    private static final long serialVersionUID = -1193747389324827610L;

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
