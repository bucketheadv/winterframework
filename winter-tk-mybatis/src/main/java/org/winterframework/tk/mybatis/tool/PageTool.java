package org.winterframework.tk.mybatis.tool;

import com.github.pagehelper.PageInfo;
import org.winterframework.core.tool.BeanTool;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:19 AM
 */
public class PageTool {
	private PageTool() {}

	public static <T> PageInfo<T> convert(PageInfo<?> pageInfo, Class<T> clazz) {
		PageInfo<T> result = new PageInfo<>();
		List<T> list = BeanTool.copyList(pageInfo.getList(), clazz);
		result.setPages(pageInfo.getPages());
		result.setPageNum(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		result.setPrePage(pageInfo.getPrePage());
		result.setHasPreviousPage(pageInfo.isHasPreviousPage());
		result.setNextPage(pageInfo.getNextPage());
		result.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
		result.setNavigateLastPage(pageInfo.getNavigateLastPage());
		result.setNavigatePages(pageInfo.getNavigatePages());
		result.setNavigatepageNums(pageInfo.getNavigatepageNums());
		result.setHasNextPage(pageInfo.isHasNextPage());
		result.setIsLastPage(pageInfo.isIsLastPage());
		result.setIsFirstPage(pageInfo.isIsFirstPage());
		result.setStartRow(pageInfo.getStartRow());
		result.setEndRow(pageInfo.getEndRow());
		result.setNavigateLastPage(pageInfo.getNavigateLastPage());
		result.setList(list);
		return result;
	}
}
