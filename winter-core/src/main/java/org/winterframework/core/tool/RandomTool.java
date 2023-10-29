package org.winterframework.core.tool;

import cn.hutool.core.util.RandomUtil;
import lombok.experimental.UtilityClass;

/**
 * @author qinglinl
 * Created on 2022/10/10 2:44 PM
 */
@UtilityClass
public class RandomTool extends RandomUtil {
    /**
     * 根据机率随机获取
     * @param probArr 概率列表
     * @return 对应的下标
     */
    public static int randomPick(double[] probArr) {
        double total = 0;
        for (double p : probArr) {
            total += p;
        }
        double random = Math.random() * total;
        for (int i = 0; i < probArr.length; i++) {
            random -= probArr[i];
            if (random <= 0) {
                return i;
            }
        }
        return 0;
    }
}
