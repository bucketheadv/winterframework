package org.winterframework.tk.mybatis.service;

import org.winterframework.tk.mybatis.mapper.crud.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:49 PM
 */
public interface TkService<Entity, ID> extends TkBaseSelectMapper<Entity, ID>,
		TkBaseInsertMapper<Entity>,
		TkBaseUpdateMapper<Entity>,
		TkBaseDeleteMapper<Entity, ID>,
		TkSelectPageMapper<Entity> {
	List<Entity> selectByIds(List<ID> ids);

	int deleteByIds(List<ID> ids);

	int updateByPrimaryKey(Entity entity);

	List<Entity> selectList(Condition condition);

	List<Entity> selectAllByPrimaryKeyDesc();
}
