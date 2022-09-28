package org.winterframework.jwt.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/9/27 2:14 PM
 */
@Getter
@AllArgsConstructor
public enum EnvironmentType {
	DEV("dev"),
	TEST("test"),
	PRE("pre"),
	PROD("prod"),
	;
	private final String value;

	private static final Map<String, EnvironmentType> map = new HashMap<>();

	static {
		for (EnvironmentType value : values()) {
			map.put(value.value, value);
		}
	}

	public static EnvironmentType parse(String value) {
		return map.get(value.toLowerCase());
	}
}
