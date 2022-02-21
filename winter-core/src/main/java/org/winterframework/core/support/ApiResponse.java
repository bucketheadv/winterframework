package org.winterframework.core.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sven
 * Created on 2022/1/28 2:52 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int code;

    private String msg;

    private T data;

    private long timestamp;

    private Object extra;
}
