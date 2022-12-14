package org.winterframework.tk.mybatis.service.base;

import org.winterframework.tk.mybatis.mapper.base.crud.*;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:49 PM
 */
public interface BaseTkService<Entity, ID> extends BaseTkSelectMapper<Entity, ID>,
		BaseTkInsertMapper<Entity>,
		BaseTkUpdateMapper<Entity>,
		BaseTkDeleteMapper<Entity, ID>,
		BaseTkSelectPageMapper<Entity>,
		MySqlMapper<Entity> {
	List<Entity> selectByIds(List<ID> ids);

	int deleteByIds(List<ID> ids);

	List<Entity> selectList(Condition condition);

	List<Entity> selectAllByPrimaryKeyDesc();
}
