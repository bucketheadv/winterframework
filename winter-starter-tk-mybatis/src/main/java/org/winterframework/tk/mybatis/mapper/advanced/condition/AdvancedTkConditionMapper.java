package org.winterframework.tk.mybatis.mapper.advanced.condition;

import org.winterframework.tk.mybatis.mapper.base.condition.BaseTkUpdateByConditionSelectiveMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:43 PM
 */
@RegisterMapper
public interface AdvancedTkConditionMapper<T> extends AdvancedUpdateByConditionMapper<T>, BaseTkUpdateByConditionSelectiveMapper<T> {
}
