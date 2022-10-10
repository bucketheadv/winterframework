package org.winterframework.tk.mybatis.service;

import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 2:04 PM
 */
public interface TkMySqlService<Entity, ID> extends TkService<Entity, ID>, MySqlMapper<Entity> {
}
