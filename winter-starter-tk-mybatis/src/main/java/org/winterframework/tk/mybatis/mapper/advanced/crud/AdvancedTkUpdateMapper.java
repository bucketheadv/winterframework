package org.winterframework.tk.mybatis.mapper.advanced.crud;

import org.winterframework.tk.mybatis.mapper.base.crud.BaseTkUpdateMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:24 PM
 */
@RegisterMapper
public interface AdvancedTkUpdateMapper<Entity> extends BaseTkUpdateMapper<Entity>, UpdateByPrimaryKeyMapper<Entity> {
}
