package org.winterframework.core.sensitive.strategy;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author sven
 * Created on 2023/3/5 11:05 PM
 */
public enum SensitiveStrategy {
    /**
     * Username sensitive strategy.  $1 替换为正则的第一组  $2 替换为正则的第二组
     */
    USERNAME(s -> {
        if (s.length() >= 4) {
            return s.replaceAll("(\\S)\\S*(\\S)", "$1**$2");
        }
        return s.replaceAll("(\\S)\\S(\\S*)", "$1*$2");
    }),
    /**
     * Id card sensitive type.
     */
    ID_CARD(s -> s.replaceAll("(\\d{3})\\d{11}(\\w{4})", "$1***********$2")),
    /**
     * Phone sensitive type.
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{3}(\\S*)\\S{4}", "$1****$2****"));


    private final Function<String, String> desensitizer;

    /**
     * 定义构造函数，传入一个函数
     */
    SensitiveStrategy(UnaryOperator<String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    /**
     * getter方法
     */
    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
