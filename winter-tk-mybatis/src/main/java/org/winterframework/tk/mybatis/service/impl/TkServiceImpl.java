package org.winterframework.tk.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.winterframework.tk.mybatis.mapper.BaseTkMapper;
import org.winterframework.tk.mybatis.service.TkService;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author qinglinl
 * Created on 2022/10/9 9:49 PM
 */
public abstract class TkServiceImpl<Mapper extends BaseTkMapper<Entity, ID>, Entity, ID> implements TkService<Entity, ID> {
	@Autowired
	protected Mapper baseMapper;

	@Override
	public List<Entity> selectByIds(List<ID> ids) {
		return baseMapper.selectByIdList(ids);
	}

	@Override
	public int deleteByIds(List<ID> ids) {
		return baseMapper.deleteByIdList(ids);
	}

	@Override
	public int updateByPrimaryKey(Entity entity) {
		return baseMapper.updateByPrimaryKey(entity);
	}

	@Override
	public Entity selectByPrimaryKey(ID id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Entity> selectList(Condition condition) {
		return baseMapper.selectByCondition(condition);
	}

	@Override
	public List<Entity> selectAllByPrimaryKeyDesc() {
		Class<?> entityClass = getEntityClass();
		String idCol = getIdColumn(entityClass);
		Condition condition = new Condition(entityClass);
		condition.orderBy(idCol).desc();
		return selectList(condition);
	}

	@Override
	public int deleteByPrimaryKey(ID id) {
		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int delete(Entity entity) {
		return baseMapper.delete(entity);
	}

	@Override
	public List<Entity> selectAll() {
		return baseMapper.selectAll();
	}

	@Override
	public int selectCount(Entity entity) {
		return baseMapper.selectCount(entity);
	}

	@Override
	public List<Entity> select(Entity entity) {
		return baseMapper.select(entity);
	}

	@Override
	public Entity selectOne(Entity entity) {
		return baseMapper.selectOne(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(Entity entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int insert(Entity entity) {
		return baseMapper.insert(entity);
	}

	@Override
	public int insertSelective(Entity entity) {
		return baseMapper.insertSelective(entity);
	}

	private Class<?> getEntityClass() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		return (Class<?>) parameterizedType.getActualTypeArguments()[1];
	}

	private String getIdColumn(Class<?> entityClass) {
		Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
		if (columnList.size() == 1) {
			EntityColumn column = columnList.iterator().next();
			return column.getColumn();
		} else {
			throw new MapperException("继承 selectList 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
		}
	}

	@Override
	public List<Entity> selectByCondition(Condition o) {
		return baseMapper.selectByCondition(o);
	}
}
