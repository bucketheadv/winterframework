package org.winterframework.core.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sven
 * Created on 2021/12/30 10:36 下午
 */
@Slf4j
public final class BeanTool {
    private BeanTool() {}

    public static <T> T copyAs(Object source, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, obj);
            return obj;
        } catch (Exception e) {
            log.error("copyAs异常: ", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> copyList(Collection<?> c, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (CollectionTool.isEmpty(c)) {
            return result;
        }
        for (Object o : c) {
            result.add(copyAs(o, clazz));
        }
        return result;
    }
}
