package org.winterframework.tk.mybatis.mapper.condition;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:26 AM
 */
@RegisterMapper
public interface TkConditionMapper<T> extends TkSelectByConditionMapper<T>,
		TkSelectCountByConditionMapper<T>,
		TkDeleteByConditionMapper<T>,
		TkUpdateByConditionMapper<T>,
		TkUpdateByConditionSelectiveMapper<T> {
}
