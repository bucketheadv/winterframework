package org.winterframework.tk.mybatis.mapper.condition;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.provider.ConditionProvider;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:26 AM
 */
@RegisterMapper
public interface TkUpdateByConditionSelectiveMapper<T> {
	@UpdateProvider(
			type = ConditionProvider.class,
			method = "dynamicSQL"
	)
	int updateByConditionSelective(@Param("record") T record, @Param("example")Condition condition);
}
