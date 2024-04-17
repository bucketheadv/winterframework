package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.TimeZone;

/**
 * @author qinglin.liu
 * created at 2024/3/1 19:51
 */
@UtilityClass
public class DateTool {
    /**
     * 格式为 yyyyMMddHH
     */
    public static final DateTimeFormatter DATE_HOUR_FORMAT = new DateTimeFormatterBuilder()
            .appendYear(4, 4)
            .appendMonthOfYear(2)
            .appendDayOfMonth(2)
            .appendHourOfDay(2).toFormatter();

    /**
     * 格式为 yyyyMMdd
     */
    public static final DateTimeFormatter DATE_BASE_FORMAT = new DateTimeFormatterBuilder()
            .appendYear(4, 4)
            .appendMonthOfYear(2)
            .appendDayOfMonth(2)
            .toFormatter();

    /**
     * 格式为 yyyy-MM-dd
     */
    public static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendYear(4, 4)
            .appendLiteral("-")
            .appendMonthOfYear(2)
            .appendLiteral("-")
            .appendDayOfMonth(2)
            .toFormatter();

    /**
     * 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
            .appendYear(4, 4)
            .appendLiteral("-")
            .appendMonthOfYear(2)
            .appendLiteral("-")
            .appendDayOfMonth(2)
            .appendLiteral(" ")
            .appendHourOfDay(2)
            .appendLiteral(":")
            .appendMinuteOfHour(2)
            .appendLiteral(":")
            .appendSecondOfMinute(2)
            .toFormatter();

    public static DateTime startOfYear(DateTime dateTime) {
        return startOfMonth(dateTime).withMonthOfYear(1);
    }

    public static DateTime endOfYear(DateTime dateTime) {
        return endOfMonth(dateTime).withMonthOfYear(12);
    }

    public static DateTime startOfMonth(DateTime dateTime) {
        return startOfDay(dateTime).withDayOfMonth(1);
    }

    public static DateTime endOfMonth(DateTime dateTime) {
        return startOfMonth(dateTime).plusMonths(1).minusMillis(1);
    }

    public static DateTime startOfDay(DateTime dateTime) {
        return startOfHour(dateTime).withHourOfDay(0);
    }

    public static DateTime endOfDay(DateTime dateTime) {
        return endOfHour(dateTime).withHourOfDay(23);
    }

    public static DateTime startOfHour(DateTime dateTime) {
        return dateTime.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    public static DateTime endOfHour(DateTime dateTime) {
        return dateTime.withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
    }

    /**
     * 时区转指定时区时间, 转换前后时间戳不变
     * 如 2024-04-16 00:00:00+00:00 转GMT+03:00后，将返回 2024-04-16 03:00:00+03:00
     * @param dateStr 时间值 如 2024-04-16 00:00:00
     * @param srcTimeZone 原时区 如 GMT+00:00
     * @param destTimeZone 目标时区 如 GMT+03:00
     * @return DateTime
     */
    public static DateTime convertTimeZone(String dateStr, String srcTimeZone, String destTimeZone) {
        // parse结果是系统时区，先直接修改时区为src时区（时间值不变，仅时区变化）
        // 再转换为dest时区的时间（时区和时间值同时变化）
        DateTime dateTime = DateTime.parse(dateStr, DATE_TIME_FORMAT);
        return dateTime.withZoneRetainFields(getDateTimeZone(srcTimeZone))
                .withZone(getDateTimeZone(destTimeZone));
    }

    /**
     * 以指定时区初始化时间
     * 如 2024-04-16 00:00:00，GMT+03:00，将返回2024-04-16 00:00:00+03:00 所代表的时间
     * @param dateStr 如 2024-04-16 00:00:00
     * @param timezone 目标时区 如 GMT+03:00
     * @return DateTime
     */
    public static DateTime parseTimeZoneRetainFields(String dateStr, String timezone) {
        DateTime dateTime = DateTime.parse(dateStr, DATE_TIME_FORMAT);
        return dateTime.withZoneRetainFields(getDateTimeZone(timezone));
    }

    /**
     * 解析时区，支持多种格式，但建议使用 GMT+08:00 这样的格式
     * @param timezone 时区 如 GMT+08:00
     * @return DateTimeZone
     */
    public static DateTimeZone getDateTimeZone(String timezone) {
        return DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone));
    }
}
