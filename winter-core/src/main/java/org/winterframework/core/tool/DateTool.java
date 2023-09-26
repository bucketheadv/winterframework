package org.winterframework.core.tool;

import cn.hutool.core.date.DateUtil;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sven
 * Created on 2021/12/30 11:02 下午
 */
@UtilityClass
public final class DateTool extends DateUtil {
    /**
     * 将时间戳转换为带时区时间对象
     * @param timeMillis 时间戳/毫秒
     * @param zoneId 时区id（ 如 UTC+8 ）
     * @return ZonedDateTime
     */
    public static ZonedDateTime fromTimeMillis(long timeMillis, String zoneId) {
        return fromTimeMillis(timeMillis, ZoneId.of(zoneId));
    }

    /**
     * 将时间戳转换为带时区时间对象
     * @param timeMillis 时间戳/毫秒
     * @param zoneId 时区id（ 如 UTC+8 ）
     * @return ZonedDateTime
     */
    public static ZonedDateTime fromTimeMillis(long timeMillis, ZoneId zoneId) {
        return Instant.ofEpochMilli(timeMillis).atZone(zoneId);
    }

    /**
     * 将带时区时间对象转换为时间戳
     * @param zonedDateTime 时区时间对象
     * @return ZonedDateTime
     */
    public static long toTimeMillis(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     * 转换带时区时间对象所在时区，转换前后两个时间的时间戳不变
     * 如 2023-09-07T11:28:29.114382[UTC+08:00] 转换后为 2023-09-07T03:28:29.114382Z[UTC]
     * @param zonedDateTime 时区对象
     * @param zoneId 目标时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime convertToZoneId(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return zonedDateTime.toInstant().atZone(zoneId);
    }

    /**
     * 转换带时区时间对象所在时区，转换前后两个时间的时间戳不变
     * 如 2023-09-07T11:28:29.114382[UTC+08:00] 转换后为 2023-09-07T03:28:29.114382Z[UTC]
     * @param zonedDateTime 时区对象
     * @param zoneId 目标时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime convertToZoneId(ZonedDateTime zonedDateTime, String zoneId) {
        return convertToZoneId(zonedDateTime, ZoneId.of(zoneId));
    }

    /**
     * 获取一天内的0点(00:00:00)
     * @param zonedDateTime 时间
     * @return ZonedDateTime
     */
    public static ZonedDateTime getStartOfDay(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取一天内的最后一刻(23:59:59)
     * @param zonedDateTime 时间
     * @return ZonedDateTime
     */
    public static ZonedDateTime getEndOfDay(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    /**
     * 获取当前时间
     * @param zoneId 时区id
     * @return ZonedDateTime
     */
    public static ZonedDateTime now(ZoneId zoneId) {
        return Instant.now().atZone(zoneId);
    }

    /**
     * 解析字符串时间
     * @param text 字符串时间
     * @param pattern 格式
     * @return ZonedDateTime
     */
    public static ZonedDateTime parse(String text, String pattern) {
        return ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将 UTC+0 的LocalDateTime转换为ZonedDateTime
     * !!!!! 注意，utcLocalDateTime 必须是 UTC 时区创建的对象
     * @param utcLocalDateTime 必须是UTC+0的LocalDateTime
     * @return ZonedDateTime
     */
    public static ZonedDateTime fromUTCLocalDateTime(LocalDateTime utcLocalDateTime) {
        return fromLocalDateTime(utcLocalDateTime, "UTC");
    }

    /**
     * 将带时区的时间转化为 UTC 的LocalDateTime
     * @param zonedDateTime localDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toUTCLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    /**
     * 将LocalDateTime转化为ZonedDateTime，直接加上时区信息，不做任何转换
     * @param localDateTime localDateTime
     * @param zoneId 该时间所属时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime fromLocalDateTime(LocalDateTime localDateTime, String zoneId) {
        return localDateTime.atZone(ZoneId.of(zoneId));
    }

    /**
     * 将LocalDateTime转化为ZonedDateTime
     * @param localDateTime localDateTime
     * @param zoneId 该时间所属时区
     * @return ZonedDateTime
     */
    public static ZonedDateTime fromLocalDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId);
    }

}
