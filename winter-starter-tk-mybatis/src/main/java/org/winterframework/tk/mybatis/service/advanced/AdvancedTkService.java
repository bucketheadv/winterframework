package org.winterframework.tk.mybatis.service.advanced;

import org.winterframework.tk.mybatis.service.base.BaseTkService;

/**
 * @author qinglinl
 * Created on 2022/12/13 3:27 PM
 */
public interface AdvancedTkService<Entity, ID> extends BaseTkService<Entity, ID> {
	int updateByPrimaryKey(Entity entity);

	int insert(Entity entity);
}
