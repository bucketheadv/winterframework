package org.winterframework.core.tool;

import cn.hutool.core.collection.CollectionUtil;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sven
 * Created on 2022/3/1 10:38 下午
 */
@UtilityClass
public final class CollectionTool extends CollectionUtil {
    /**
     * 将数组进行分组，每组最多多少个元素
     * @param list
     * @param size
     * @return
     * @param <T>
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        int len = new BigDecimal(list.size())
                .divide(BigDecimal.valueOf(size), RoundingMode.CEILING).intValue();
        for (int i = 0; i < len; i++) {
            int idx = i * size;
            List<T> tmp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (idx + j < list.size()) {
                    tmp.add(list.get(idx + j));
                }
            }
            result.add(tmp);
        }
        return result;
    }
}
