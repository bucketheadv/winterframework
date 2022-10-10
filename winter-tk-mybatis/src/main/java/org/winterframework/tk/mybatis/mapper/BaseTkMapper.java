package org.winterframework.tk.mybatis.mapper;

import org.winterframework.tk.mybatis.mapper.condition.TkConditionMapper;
import org.winterframework.tk.mybatis.mapper.crud.*;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.rowbounds.SelectRowBoundsMapper;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:42 PM
 */
@RegisterMapper
public interface BaseTkMapper<Entity, ID> extends TkBaseInsertMapper<Entity>,
		TkBaseDeleteMapper<Entity, ID>,
		TkBaseUpdateMapper<Entity>,
		TkBaseSelectMapper<Entity, ID>,
		SelectRowBoundsMapper<Entity>,
		TkConditionMapper<Entity>,
		SelectByIdListMapper<Entity, ID>,
		DeleteByIdListMapper<Entity, ID>,
		TkSelectPageMapper<Entity> {
}
