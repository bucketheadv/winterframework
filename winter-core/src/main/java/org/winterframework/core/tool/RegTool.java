package org.winterframework.core.tool;

import cn.hutool.core.util.ReUtil;
import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * @author qinglinl
 * Created on 2022/9/28 11:48 AM
 */
@UtilityClass
public class RegTool extends ReUtil {
	public static boolean isMatch(String s, Collection<String> patterns) {
		if (StringTool.isBlank(s)) {
			return false;
		}
		for (String pattern : patterns) {
			if (isMatch(pattern, s)) {
				return true;
			}
		}
		return false;
	}
}
