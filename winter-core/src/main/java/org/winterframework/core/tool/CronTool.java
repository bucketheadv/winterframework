package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
                                                  TimeZone timeZone,
                                                  int count) throws ParseException {
        CronExpression cronExpression = new CronExpression(cron);
        cronExpression.setTimeZone(timeZone);
        Date now = new Date();
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Date nextTriggerTime = cronExpression.getNextValidTimeAfter(now);
            if (nextTriggerTime == null) {
                break;
            }

            dateList.add(formatDateTime(nextTriggerTime));
            now = nextTriggerTime;
        }
        return dateList;
    }

    private String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
