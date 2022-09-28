package org.winterframework.core.tool;

import cn.hutool.core.util.ReUtil;

import java.util.Collection;

/**
 * @author qinglinl
 * Created on 2022/9/28 11:48 AM
 */
public class RegTool {
	public static boolean isMatch(String s, Collection<String> patterns) {
		if (StringTool.isNotBlank(s)) {
			for (String pattern : patterns) {
				if (ReUtil.isMatch(pattern, s)) {
					return true;
				}
			}
		}
		return false;
	}
}
