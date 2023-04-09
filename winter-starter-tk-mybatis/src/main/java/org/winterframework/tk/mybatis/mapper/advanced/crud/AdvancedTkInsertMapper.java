package org.winterframework.tk.mybatis.mapper.advanced.crud;

import org.winterframework.tk.mybatis.mapper.base.crud.BaseTkInsertMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.insert.InsertMapper;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:25 PM
 */
@RegisterMapper
public interface AdvancedTkInsertMapper<Entity> extends BaseTkInsertMapper<Entity>, InsertMapper<Entity> {
}
