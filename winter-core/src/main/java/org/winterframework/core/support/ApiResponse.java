package org.winterframework.core.support;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sven
 * Created on 2022/1/28 2:52 下午
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1714714325730785477L;

    private int code;

    private String message;

    private T data;

    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    private Object extra;
}
