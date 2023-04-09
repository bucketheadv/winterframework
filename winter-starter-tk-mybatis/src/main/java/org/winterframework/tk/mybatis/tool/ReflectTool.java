package org.winterframework.tk.mybatis.tool;

import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Set;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:38 PM
 */
public class ReflectTool {
	public static String getIdColumn(Class<?> entityClass) {
		Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
		if (columnList.size() == 1) {
			EntityColumn column = columnList.iterator().next();
			return column.getColumn();
		} else {
			throw new MapperException("继承 selectList 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
		}
	}
}
