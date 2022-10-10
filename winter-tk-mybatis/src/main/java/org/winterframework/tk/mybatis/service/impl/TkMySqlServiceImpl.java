package org.winterframework.tk.mybatis.service.impl;

import org.winterframework.tk.mybatis.mapper.BaseTkMySqlMapper;
import org.winterframework.tk.mybatis.service.TkMySqlService;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/10 2:06 PM
 */
public abstract class TkMySqlServiceImpl<Mapper extends BaseTkMySqlMapper<Entity, ID>, Entity, ID>
	extends TkServiceImpl<Mapper, Entity, ID>
		implements TkMySqlService<Entity, ID> {
	@Override
	public int insertList(List<? extends Entity> list) {
		return baseMapper.insertList(list);
	}

	@Override
	public int insertUseGeneratedKeys(Entity entity) {
		return baseMapper.insertUseGeneratedKeys(entity);
	}
}
