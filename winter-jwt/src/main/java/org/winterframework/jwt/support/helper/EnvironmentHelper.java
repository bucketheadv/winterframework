package org.winterframework.jwt.support.helper;

import org.apache.commons.lang3.StringUtils;
import org.winterframework.jwt.support.enums.EnvironmentType;

/**
 * @author qinglinl
 * Created on 2022/9/27 2:13 PM
 */
public class EnvironmentHelper {
	public static EnvironmentType findEnvironmentType() {
		String env = System.getProperty("spring.profiles.active");
		if (StringUtils.isNotBlank(env)) {
			EnvironmentType environmentType = EnvironmentType.parse(env);
			if (environmentType != null) {
				return environmentType;
			}
		}
		return EnvironmentType.DEV;
	}
}
