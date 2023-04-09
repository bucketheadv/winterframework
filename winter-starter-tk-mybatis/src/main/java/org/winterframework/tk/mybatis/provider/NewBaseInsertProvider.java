package org.winterframework.tk.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

/**
 * @author sven
 * Created on 2023/3/11 9:45 PM
 */
public class NewBaseInsertProvider extends BaseInsertProvider {
    public NewBaseInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insertSelectiveUseGeneratedKeys(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, true, false));
        sql.append(SqlHelper.insertValuesColumns(entityClass, true, true, false));
        EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);
        return sql.toString();
    }
}
