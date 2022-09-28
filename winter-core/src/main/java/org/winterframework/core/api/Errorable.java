package org.winterframework.core.api;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:26 PM
 */
public interface Errorable {
	int getCode();

	/**
	 * return i18n code!!!!
	 */
	String getI18nCode();
}
