package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qinglin.liu
 * @create 2023/9/26 19:23
 */
@UtilityClass
public class CronTool {
    /**
     * 根据cron表达式获取下n次执行时间点
     * @param cron cron表达式
     * @param count 要获取的个数
     * @return
     * @throws ParseException
     */
    public static List<String> getNextTriggerTime(String cron,
                                                  DateTimeZone dateTimeZone,
                                                  int count) throws ParseException {
        CronExpression cronExpression = new CronExpression(cron);
        Date now = new Date();
        cronExpression.setTimeZone(dateTimeZone.toTimeZone());
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Date nextTriggerTime = cronExpression.getNextValidTimeAfter(now);
            if (nextTriggerTime == null) {
                break;
            }
            DateTime realTriggerTime = new DateTime(nextTriggerTime).withZone(dateTimeZone);
            dateList.add(realTriggerTime.toString("yyyy-MM-dd HH:mm:ss"));
            now = nextTriggerTime;
        }
        return dateList;
    }
}
