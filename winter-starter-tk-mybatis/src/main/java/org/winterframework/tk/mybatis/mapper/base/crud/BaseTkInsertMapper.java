package org.winterframework.tk.mybatis.mapper.base.crud;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.winterframework.tk.mybatis.provider.NewBaseInsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;

/**
 * @author qinglinl
 * Created on 2022/10/10 8:09 AM
 */
@RegisterMapper
public interface BaseTkInsertMapper<Entity> extends InsertSelectiveMapper<Entity> {
    @Options(
            useGeneratedKeys = true
    )
    @InsertProvider(
            type = NewBaseInsertProvider.class,
            method = "dynamicSQL"
    )
    int insertSelectiveUseGeneratedKeys(Entity entity);
}
