package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author qinglinl
 * Created on 2022/9/28 11:48 AM
 */
@UtilityClass
public class RegTool {
	public static boolean isMatch(String s, Collection<String> patterns) {
		if (StringTool.isBlank(s)) {
			return false;
		}
		for (String pattern : patterns) {
            boolean isMatch = StringUtils.countMatches(s, pattern) > 0;
            if (isMatch) {
                return true;
            }
		}
		return false;
	}
}
