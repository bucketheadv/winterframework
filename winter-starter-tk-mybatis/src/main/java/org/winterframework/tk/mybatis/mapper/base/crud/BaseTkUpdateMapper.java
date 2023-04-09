package org.winterframework.tk.mybatis.mapper.base.crud;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeySelectiveMapper;

/**
 * @author qinglinl
 * Created on 2022/10/9 11:54 PM
 */
@RegisterMapper
public interface BaseTkUpdateMapper<Entity> extends UpdateByPrimaryKeySelectiveMapper<Entity> {
}
