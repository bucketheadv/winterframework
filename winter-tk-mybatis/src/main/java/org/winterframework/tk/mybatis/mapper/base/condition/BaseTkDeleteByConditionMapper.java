package org.winterframework.tk.mybatis.mapper.base.condition;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.provider.ConditionProvider;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:24 AM
 */
@RegisterMapper
public interface BaseTkDeleteByConditionMapper<T> {
	@DeleteProvider(
			type = ConditionProvider.class,
			method = "dynamicSQL"
	)
	int deleteByCondition(Condition condition);
}
