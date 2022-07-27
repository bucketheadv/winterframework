package org.winterframework.core.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sven
 * Created on 2022/1/28 2:52 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8829798896960629148L;

    private int code;

    private String msg;

    private T data;

    private long timestamp;

    private Object extra;
}
