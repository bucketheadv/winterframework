package org.winterframework.tk.mybatis.mapper.crud;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.winterframework.tk.mybatis.mapper.condition.TkSelectByConditionMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/10 8:26 AM
 */
@RegisterMapper
public interface TkSelectPageMapper<Entity> extends TkSelectByConditionMapper<Entity> {
	default PageInfo<Entity> selectByPage(Condition condition, RowBounds rowBounds) {
		try (Page<?> ignored = PageHelper.offsetPage(rowBounds.getOffset(), rowBounds.getLimit())) {
			List<Entity> result = selectByCondition(condition);
			return new PageInfo<>(result);
		}
	}

	default PageInfo<Entity> selectByPage(Condition condition, int pageNum, int pageSize) {
		try (Page<?> ignored = PageHelper.startPage(pageNum, pageSize)) {
			List<Entity> result = selectByCondition(condition);
			return new PageInfo<>(result);
		}
	}
}
