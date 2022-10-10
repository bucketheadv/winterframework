package org.winterframework.tk.mybatis.mapper.crud;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.delete.DeleteMapper;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:56 PM
 */
@RegisterMapper
public interface TkBaseDeleteMapper<Entity, ID> extends DeleteMapper<Entity> {
	@DeleteProvider(
			type = BaseDeleteProvider.class,
			method = "dynamicSQL"
	)
	int deleteByPrimaryKey(ID id);
}
