package org.winterframework.tk.mybatis.mapper;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 1:54 PM
 */
@RegisterMapper
public interface BaseTkMySqlMapper<Entity, ID> extends BaseTkMapper<Entity, ID>, MySqlMapper<Entity> {
}
