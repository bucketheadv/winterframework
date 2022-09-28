package org.winterframework.jwt.interceptor;

import org.winterframework.jwt.env.Environment;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:18 PM
 */
public class EnvironmentHolder {
	private static final ThreadLocal<Environment> environmentThreadLocal = new ThreadLocal<>();

	public static void put(Environment env) {
		environmentThreadLocal.set(env);
	}

	public static Environment get() {
		return environmentThreadLocal.get();
	}

	public static void clear() {
		environmentThreadLocal.remove();
	}
}
