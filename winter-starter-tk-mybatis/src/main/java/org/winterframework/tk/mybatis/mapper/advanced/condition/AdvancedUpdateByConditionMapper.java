package org.winterframework.tk.mybatis.mapper.advanced.condition;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.provider.ConditionProvider;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:42 PM
 */
@RegisterMapper
public interface AdvancedUpdateByConditionMapper<T> {
	@UpdateProvider(
			type = ConditionProvider.class,
			method = "dynamicSQL"
	)
	int updateByCondition(@Param("record") T entity, @Param("example") Condition condition);
}
