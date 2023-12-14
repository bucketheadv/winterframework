package org.winterframework.core.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeZoneEnum {

    //UTC 0时区
    UTC(1, "+0"),
    //东一区
    UTC_E1(2, "+1"),
    //东二区
    UTC_E2(3, "+2"),
    //东三区
    UTC_E3(4, "+3"),
    //东四区
    UTC_E4(5, "+4"),
    //东五区
    UTC_E5(6, "+5"),
    //东六区
    UTC_E6(7, "+6"),
    //东七区
    UTC_E7(8, "+7"),
    //东八区
    UTC_E8(9, "+8"),
    //东九区
    UTC_E9(10, "+9"),
    //东十区
    UTC_E10(11, "+10"),
    //东十一区
    UTC_E11(12, "+11"),
    //东十二区
    UTC_E12(13, "+12"),
    //西一区
    UTC_W1(14, "-1"),
    //西二区
    UTC_W2(15, "-2"),
    //西三区
    UTC_W3(16, "-3"),
    //西四区
    UTC_W4(17, "-4"),
    //西五区
    UTC_W5(18, "-5"),
    //西六区
    UTC_W6(19, "-6"),
    //西七区
    UTC_W7(20, "-7"),
    //西八区
    UTC_W8(21, "-8"),
    //西九区
    UTC_W9(22, "-9"),
    //西十区
    UTC_W10(23, "-10"),
    //西十一区
    UTC_W11(24, "-11"),
    //西十二区
    UTC_W12(25, "-12"),
    //东3.5区
    UTC_E3_30(26, "+03:30"),
    //东4.5区
    UTC_E4_30(27, "+04:30"),
    //东5.5区
    UTC_E5_30(28, "+05:30"),
    //东6.5区
    UTC_E6_30(29, "+06:30"),
    //东8.45区
    UTC_E8_45(30, "+08:45"),
    //东9.30区
    UTC_E9_30(31, "+09:30"),
    //东10.30区
    UTC_E10_30(32, "+10:30"),
    ;

    private final int code;
    private final String zone;

    public static TimeZoneEnum getByCode(Integer code) {
        for (TimeZoneEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
