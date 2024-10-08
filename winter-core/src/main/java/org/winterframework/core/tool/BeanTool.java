package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sven
 * Created on 2021/12/30 10:36 下午
 */
@Slf4j
@UtilityClass
public final class BeanTool {

    public static <T> T copyAs(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
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
        if (CollectionUtils.isEmpty(c)) {
            return result;
        }
        for (Object o : c) {
            result.add(copyAs(o, clazz));
        }
        return result;
    }
}
