package org.winterframework.tk.mybatis.mapper.base.condition;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:26 AM
 */
@RegisterMapper
public interface BaseTkConditionMapper<T> extends BaseTkSelectByConditionMapper<T>,
		BaseTkSelectCountByConditionMapper<T>,
		BaseTkDeleteByConditionMapper<T>,
		BaseTkUpdateByConditionSelectiveMapper<T> {
}
