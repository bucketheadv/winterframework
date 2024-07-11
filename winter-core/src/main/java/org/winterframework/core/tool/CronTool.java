package org.winterframework.core.tool;

import cn.hutool.core.date.DateUtil;
import lombok.experimental.UtilityClass;
import org.quartz.CronExpression;

import java.text.ParseException;
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
    public static List<String> getNextValidTimeAfter(String cron, TimeZone timeZone, int count) throws ParseException {
        CronExpression cronExpression = new CronExpression(cron);
        cronExpression.setTimeZone(timeZone);
        Date now = new Date();
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Date tmp = cronExpression.getNextValidTimeAfter(now);
            if (tmp == null) {
                break;
            }
            dateList.add(DateUtil.formatDateTime(tmp));
            now = tmp;
        }
        return dateList;
    }
}
