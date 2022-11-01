package org.winterframework.cache.generator;

import org.winterframework.core.tool.StringTool;

import java.lang.reflect.Method;

/**
 * @author qinglinl
 * Created on 2022/10/27 2:09 PM
 */
public class WinterCacheKeyGenerator {
	public String invoke(Method method, Object ...params) {
		return StringTool.join(params, "::");
	}
}
