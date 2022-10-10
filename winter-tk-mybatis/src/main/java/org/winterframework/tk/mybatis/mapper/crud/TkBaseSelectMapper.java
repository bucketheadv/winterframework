package org.winterframework.tk.mybatis.mapper.crud;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.select.*;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:51 PM
 */
@RegisterMapper
public interface TkBaseSelectMapper<Entity, ID> extends SelectOneMapper<Entity>,
		SelectMapper<Entity>, SelectAllMapper<Entity>,
		SelectCountMapper<Entity> {
	@SelectProvider(
			type = BaseSelectProvider.class,
			method = "dynamicSQL"
	)
	Entity selectByPrimaryKey(ID id);
}
