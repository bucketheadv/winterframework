package org.winterframework.tk.mybatis.mapper.condition;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.provider.ConditionProvider;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/10 9:22 AM
 */
@RegisterMapper
public interface TkSelectByConditionMapper<T> {
	@SelectProvider(
			type = ConditionProvider.class,
			method = "dynamicSQL"
	)
	List<T> selectByCondition(Condition condition);
}
