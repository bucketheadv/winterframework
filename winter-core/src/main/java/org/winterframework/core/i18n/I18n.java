package org.winterframework.core.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:19 PM
 */
@Slf4j
@Component
public class I18n {
	private static MessageSource messageSource;

	public I18n(MessageSource messageSource) {
		I18n.messageSource = messageSource;
	}

	public static String get(String key) {
		return get(key, (Object) null);
	}

	public static String getOrDefault(String key, Object[] params, String defaultValue) {
		String val = get(key, params);
		if (Objects.equals(key, val)) {
			return defaultValue;
		}
		return val;
	}

	public static String getOrDefault(String key, String defaultValue) {
		String val = get(key);
		if (Objects.equals(key, val)) {
			return defaultValue;
		}
		return val;
	}

	public static String get(String key, Object ...params) {
		String msg = null;
		Locale locale = LocaleContextHolder.getLocale();
		try {
			msg = messageSource.getMessage(key, params, locale);
		} catch (NoSuchMessageException e) {
			log.warn("Get I18n message failed, no such key: {}, locale: {}", key, locale);
		}
		return msg == null ? key : msg;
	}
}
