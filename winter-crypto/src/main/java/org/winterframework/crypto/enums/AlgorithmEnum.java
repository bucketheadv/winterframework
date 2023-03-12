package org.winterframework.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sven
 * Created on 2023/3/12 5:51 PM
 */
@Getter
@AllArgsConstructor
public enum AlgorithmEnum {
    AES("AES");
    private final String code;
}
