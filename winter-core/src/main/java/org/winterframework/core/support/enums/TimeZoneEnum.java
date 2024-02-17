package org.winterframework.core.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeZoneEnum {

    //UTC 0时区
    UTC(1, "GMT+00:00"),
    //东一区
    UTC_E1(2, "GMT+01:00"),
    //东二区
    UTC_E2(3, "GMT+02:00"),
    //东三区
    UTC_E3(4, "GMT+03:00"),
    //东四区
    UTC_E4(5, "GMT+04:00"),
    //东五区
    UTC_E5(6, "GMT+05:00"),
    //东六区
    UTC_E6(7, "GMT+06:00"),
    //东七区
    UTC_E7(8, "GMT+07:00"),
    //东八区
    UTC_E8(9, "GMT+08:00"),
    //东九区
    UTC_E9(10, "GMT+09:00"),
    //东十区
    UTC_E10(11, "GMT+10:00"),
    //东十一区
    UTC_E11(12, "GMT+11:00"),
    //东十二区
    UTC_E12(13, "GMT+12:00"),
    //西一区
    UTC_W1(14, "GMT-01:00"),
    //西二区
    UTC_W2(15, "GMT-02:00"),
    //西三区
    UTC_W3(16, "GMT-03:00"),
    //西四区
    UTC_W4(17, "GMT-04:00"),
    //西五区
    UTC_W5(18, "GMT-05:00"),
    //西六区
    UTC_W6(19, "GMT-06:00"),
    //西七区
    UTC_W7(20, "GMT-07:00"),
    //西八区
    UTC_W8(21, "GMT-08:00"),
    //西九区
    UTC_W9(22, "GMT-09:00"),
    //西十区
    UTC_W10(23, "GMT-10:00"),
    //西十一区
    UTC_W11(24, "GMT-11:00"),
    //西十二区
    UTC_W12(25, "GMT-12:00"),
    //东3.5区
    UTC_E3_30(26, "GMT+03:30"),
    //东4.5区
    UTC_E4_30(27, "GMT+04:30"),
    //东5.5区
    UTC_E5_30(28, "GMT+05:30"),
    //东6.5区
    UTC_E6_30(29, "GMT+06:30"),
    //东8.45区
    UTC_E8_45(30, "GMT+08:45"),
    //东9.30区
    UTC_E9_30(31, "GMT+09:30"),
    //东10.30区
    UTC_E10_30(32, "GMT+10:30"),
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
