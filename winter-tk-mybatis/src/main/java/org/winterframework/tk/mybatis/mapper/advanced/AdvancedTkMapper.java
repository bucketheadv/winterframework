package org.winterframework.tk.mybatis.mapper.advanced;

import org.winterframework.tk.mybatis.mapper.advanced.condition.AdvancedTkConditionMapper;
import org.winterframework.tk.mybatis.mapper.advanced.crud.AdvancedTkInsertMapper;
import org.winterframework.tk.mybatis.mapper.advanced.crud.AdvancedTkUpdateMapper;
import org.winterframework.tk.mybatis.mapper.base.crud.BaseTkDeleteMapper;
import org.winterframework.tk.mybatis.mapper.base.crud.BaseTkSelectMapper;
import org.winterframework.tk.mybatis.mapper.base.crud.BaseTkSelectPageMapper;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.rowbounds.SelectRowBoundsMapper;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:21 PM
 */
@RegisterMapper
public interface AdvancedTkMapper<Entity, ID> extends AdvancedTkInsertMapper<Entity>,
		BaseTkDeleteMapper<Entity, ID>,
		AdvancedTkUpdateMapper<Entity>,
		BaseTkSelectMapper<Entity, ID>,
		SelectRowBoundsMapper<Entity>,
		AdvancedTkConditionMapper<Entity>,
		SelectByIdListMapper<Entity, ID>,
		DeleteByIdListMapper<Entity, ID>,
		BaseTkSelectPageMapper<Entity>,
		MySqlMapper<Entity> {
}
