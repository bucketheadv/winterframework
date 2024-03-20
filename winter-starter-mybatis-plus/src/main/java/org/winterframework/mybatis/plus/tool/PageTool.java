package org.winterframework.mybatis.plus.tool;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.winterframework.core.tool.BeanTool;

import java.util.List;

/**
 * @author qinglin.liu
 * created at 2024/3/19 15:20
 */
public class PageTool {
    @SuppressWarnings("unchecked")
    public static <T> Page<T> convert(Page<?> pageInfo, Class<T> clazz) {
        Page<T> result = BeanTool.copyAs(pageInfo, Page.class);
        List<T> list = BeanTool.copyList(pageInfo.getRecords(), clazz);
        result.setRecords(list);
        return result;
    }
}
