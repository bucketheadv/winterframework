package org.winterframework.tk.mybatis.mapper.base.condition;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.provider.ConditionProvider;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:23 AM
 */
@RegisterMapper
public interface BaseTkSelectCountByConditionMapper<T> {
	@SelectProvider(
			type = ConditionProvider.class,
			method = "dynamicSQL"
	)
	int selectCountByCondition(Condition condition);
}
