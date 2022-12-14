package org.winterframework.tk.mybatis.mapper.base;

import org.winterframework.tk.mybatis.mapper.base.condition.BaseTkConditionMapper;
import org.winterframework.tk.mybatis.mapper.base.crud.*;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.rowbounds.SelectRowBoundsMapper;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:42 PM
 */
@RegisterMapper
public interface BaseTkMapper<Entity, ID> extends BaseTkInsertMapper<Entity>,
		BaseTkDeleteMapper<Entity, ID>,
		BaseTkUpdateMapper<Entity>,
		BaseTkSelectMapper<Entity, ID>,
		SelectRowBoundsMapper<Entity>,
		BaseTkConditionMapper<Entity>,
		SelectByIdListMapper<Entity, ID>,
		DeleteByIdListMapper<Entity, ID>,
		BaseTkSelectPageMapper<Entity>,
		MySqlMapper<Entity> {
}
