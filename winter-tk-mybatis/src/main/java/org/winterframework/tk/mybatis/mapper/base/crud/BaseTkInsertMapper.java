package org.winterframework.tk.mybatis.mapper.base.crud;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 8:09 AM
 */
@RegisterMapper
public interface BaseTkInsertMapper<Entity> extends InsertSelectiveMapper<Entity> {
}
