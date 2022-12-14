package org.winterframework.tk.mybatis.service.advanced.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.winterframework.tk.mybatis.mapper.advanced.AdvancedTkMapper;
import org.winterframework.tk.mybatis.service.advanced.AdvancedTkService;
import org.winterframework.tk.mybatis.tool.ReflectTool;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:29 PM
 */
public abstract class AdvancedTkServiceImpl<Mapper extends AdvancedTkMapper<Entity, ID>, Entity, ID> implements AdvancedTkService<Entity, ID> {
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
		String idCol = ReflectTool.getIdColumn(entityClass);
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
	public int insertSelective(Entity entity) {
		return baseMapper.insertSelective(entity);
	}

	private Class<?> getEntityClass() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		return (Class<?>) parameterizedType.getActualTypeArguments()[1];
	}

	@Override
	public List<Entity> selectByCondition(Condition o) {
		return baseMapper.selectByCondition(o);
	}

	@Override
	public int insertList(List<? extends Entity> recordList) {
		return baseMapper.insertList(recordList);
	}

	@Override
	public int insertUseGeneratedKeys(Entity record) {
		return baseMapper.insertUseGeneratedKeys(record);
	}

	@Override
	public int insert(Entity record) {
		return baseMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(Entity record) {
		return baseMapper.updateByPrimaryKey(record);
	}
}
