package org.winterframework.core.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author sven
 * Created on 2021/12/30 10:36 下午
 */
@Slf4j
public final class BeanTool {
    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    private BeanTool() {}

    public static <T> T copyAs(Object source, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            getBeanCopier(source.getClass(), clazz).copy(source, obj, null);
            return obj;
        } catch (Exception e) {
            log.error("BeanTool#copyAs异常: ", e);
        }
        return null;
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

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> destClass) {
        String key = sourceClass.getName() + destClass.getName();
        BeanCopier beanCopier = beanCopierMap.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, destClass, false);
            beanCopierMap.putIfAbsent(key, beanCopier);
        }
        return beanCopier;
    }
}
